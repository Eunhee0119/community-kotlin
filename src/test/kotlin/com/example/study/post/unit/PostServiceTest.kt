package com.example.study.post.unit

import com.example.study.member.domain.Gender
import com.example.study.member.domain.Member
import com.example.study.member.unit.fixture.MemberFixture
import com.example.study.post.application.PostService
import com.example.study.post.application.dto.PostRequest
import com.example.study.post.application.dto.PostUpdateRequest
import com.example.study.post.domain.Post
import com.example.study.post.repository.PostRepository
import com.example.study.post.unit.fixture.PostFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.anyVararg
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


@DisplayName("게시글 서비스 레이어 테스트")
@ExtendWith(MockitoExtension::class)
class PostServiceTest {

    @Mock
    private val postRepository: PostRepository? = null

    @InjectMocks
    private val postService: PostService? = null

    var 회원: Member? = null

    @BeforeEach
    fun setUp() {
        val email = "test@email.com"
        val password = "password11@@"
        val nickname = "테스트계정"
        val name = "test"
        val age = 20
        val birthDate = LocalDate.parse("1990-01-01", DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        val gender = Gender.WOMAN
        회원 = MemberFixture.회원_등록(email, password, nickname, name, age, birthDate, gender)
    }

    @DisplayName("게시글을 등록한다.")
    @Test
    fun addPost() {
        // given
        val title = "게시글 제목"
        val content = "게시글 내용"
        val postRequest = PostRequest(null, title, content)

        // when
        val post = postService!!.postContent(회원, postRequest)

        // then
        verify(postRepository)!!.save(anyVararg(Post::class))
        assertThat(post.title).isEqualTo(title)
        assertThat(post.content).isEqualTo(content)
        assertThat(post.member.id).isEqualTo(회원!!.id)
    }


    @DisplayName("게시글을 조회한다.")
    @Test
    fun getPost() {
        // given
        val title = "게시글 제목"
        val content = "게시글 내용"
        val post = PostFixture.게시글_등록(title, content, 회원)

        given(postRepository!!.findById(post.id!!)).willAnswer { Optional.of(post) }

        // when
        val postResponse = postService!!.getPost(post.id!!)

        // then
        verify(postRepository)!!.findById(post.id!!)
        assertThat(postResponse.title).isEqualTo(title)
        assertThat(postResponse.content).isEqualTo(content)
        assertThat(postResponse.member.id).isEqualTo(회원!!.id)
    }


    @DisplayName("게시글을 조회에 실패한다. - 존재하지 않는 게시글을 조회한 경우")
    @Test
    fun getPostExceptionWhenNoExistPost() {
        // given
        val postId = 10L
        given(postRepository!!.findById(postId)).willThrow()

        Assertions.assertThrows(RuntimeException::class.java) { postService!!.getPost(postId) }
    }

    @DisplayName("게시글을 수정한다")
    @Test
    fun updatePost() {
        // given
        val post = PostFixture.게시글_등록("게시글 제목", "게시글 내용", 회원)
        given(postRepository!!.findById(post.id!!)).willAnswer { Optional.of(post) }

        // when
        val title = "게시글 제목 변경"
        val content = "게시글 내용 변경"
        val postUpdateRequest = PostUpdateRequest(post.id!!, title, content)
        val postResponse = postService!!.updatePost(회원!!.id!!, postUpdateRequest)

        // then
        verify(postRepository)!!.save(anyVararg(Post::class))
        assertThat(postResponse.title).isEqualTo(title)
        assertThat(postResponse.content).isEqualTo(content)
        assertThat(postResponse.member.id).isEqualTo(회원!!.id)
    }

    @DisplayName("게시글 수정에 실패한다. - 권한이 없는 글을 수정할 경우")
    @Test
    fun updatePostExceptionWhenNoPermission() {
        // given
        val post = PostFixture.게시글_등록("게시글 제목", "게시글 내용", 회원)
        given(postRepository!!.findById(post.id!!)).willAnswer { Optional.of(post) }

        // when
        val title = "게시글 제목 변경"
        val content = "게시글 내용 변경"
        val postUpdateRequest = PostUpdateRequest(post.id!!, title, content)

        Assertions.assertThrows(RuntimeException::class.java) {
            postService!!.updatePost(
                회원!!.id!! + 1000,
                postUpdateRequest
            )
        }
    }

    @DisplayName("게시글 목록을 조회한다.")
    @Test
    fun getPostList() {
        // given
        val post = PostFixture.게시글_등록("게시글 제목", "게시글 내용", 회원)
        given(postRepository!!.findById(post.id!!)).willAnswer { Optional.of(post) }

        // when
        val title = "게시글 제목 변경"
        val content = "게시글 내용 변경"
        val postUpdateRequest = PostUpdateRequest(post.id!!, title, content)

        Assertions.assertThrows(RuntimeException::class.java) {
            postService!!.updatePost(
                회원!!.id!! + 1000,
                postUpdateRequest
            )
        }
    }


}