package com.example.study.post

import com.example.study.member.MemberSteps.로그인_토큰
import com.example.study.member.MemberSteps.회원_생성_요청_반환
import com.example.study.post.PostSteps.게시글_등록
import com.example.study.post.PostSteps.게시글_수정
import com.example.study.post.PostSteps.게시글_조회
import com.example.study.post.application.dto.PostResponse
import com.example.study.post.application.dto.PostUpdateRequest
import com.example.study.util.AcceptanceTest
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

@DisplayName("게시판 기능 인수테스트")
class PostAcceptanceTest : AcceptanceTest() {

    val EMAIL = "test@email.com"
    val PASSWORD = "password11@@"
    val NICKNAME = "테스트계정"
    val NAME = "test"

    var token : String? = null

    @BeforeEach
    fun setting() {
        회원_생성_요청_반환(EMAIL, PASSWORD, NICKNAME, NAME)
        token = 로그인_토큰(EMAIL, PASSWORD)!!.accessToken
    }

    @DisplayName("게시글을 등록한다.")
    @Test
    fun postBoard(){
        var 제목 = "제목"
        var 내용 = "게시글 등록 테스트입니다."
        val 게시판_등록_응답 = 게시글_등록 (token, 제목, 내용)

        assertThat(게시판_등록_응답.statusCode()).isEqualTo(HttpStatus.CREATED.value())
        val postResponse = 게시판_등록_응답.`as`(PostResponse::class.java)
        assertThat(postResponse.title).isEqualTo(제목)
        assertThat(postResponse.content).isEqualTo(내용)
        assertThat(postResponse.member.email).isEqualTo(EMAIL)
    }

    @DisplayName("게시글을 등록에 실패한다. - 제목을 입력하지 않은 경우")
    @Test
    fun postBoardExceptionWhenBlankTitle(){
        var 제목 = ""
        var 내용 = "게시글 등록 테스트입니다."
        val 게시판_등록_응답 = 게시글_등록 (token, 제목, 내용)

        assertThat(게시판_등록_응답.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value())
    }

    @DisplayName("게시글을 조회한다.")
    @Test
    fun getPost(){
        var 제목 = "제목"
        var 내용 = "게시글 등록 테스트입니다."
        val 게시판_등록_응답 = 게시글_등록 (token, 제목, 내용)
        val post = 게시판_등록_응답.`as`(PostResponse::class.java)

        val 게시글_조회_응답 = 게시글_조회(token, post.id)
        assertThat(게시글_조회_응답.statusCode()).isEqualTo(HttpStatus.OK.value())

        val postResponse = 게시글_조회_응답.`as`(PostResponse::class.java)
        assertThat(postResponse.title).isEqualTo(제목)
        assertThat(postResponse.content).isEqualTo(내용)
        assertThat(postResponse.member.email).isEqualTo(EMAIL)
    }


    @DisplayName("게시글을 수정한다.")
    @Test
    fun updatePost(){
        val 게시판_등록_응답 = 게시글_등록 (token, "제목", "게시글 등록 테스트입니다.")
        val 게시글 = 게시판_등록_응답.`as`(PostResponse::class.java)

        val 변경후_제목 = "제목변경"
        val 변경후_내용 = "게시글 등록 테스트입니다. -> 수정됨"
        val post = PostUpdateRequest(게시글.id!!,변경후_제목,변경후_내용)
        val 게시글_수정_응답 = 게시글_수정(token, post)
        assertThat(게시글_수정_응답.statusCode()).isEqualTo(HttpStatus.OK.value())

        val postResponse = 게시글_조회(token, post.id).`as`(PostResponse::class.java)
        assertThat(postResponse.title).isEqualTo(변경후_제목)
        assertThat(postResponse.content).isEqualTo(변경후_내용)
    }

    @DisplayName("게시글 목록을 조회한다.")
    @Test
    fun getBoard(){

        var 제목1 = "제목"
        var 내용1 = "게시글 등록 테스트입니다."
        게시글_등록 (token, 제목1, 내용1)

        var 제목2 = "제목222"
        var 내용2 = "게시글 등록 테스트입니다.222"
        게시글_등록 (token, 제목2, 내용2)

        PostSteps.게시판_조회(token,1)
    }

    /*@DisplayName("게시판을 조회한다.")
    @Test
    fun getBoardPerPage(){

        var 제목 = "제목"
        var 내용 = "게시글 등록 테스트입니다."
        for (i in 0..10){
            게시글_등록 (token, 제목+i, 내용+i)
        }

        PostSteps.게시판_조회(token,1)
    }*/
}