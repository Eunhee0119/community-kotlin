package com.example.study.member

import com.example.study.auth.token.dto.TokenDto
import com.example.study.member.MemberSteps.로그인_토큰
import com.example.study.member.MemberSteps.회원_생성_요청_반환
import com.example.study.member.MemberSteps.회원_정보_조회
import com.example.study.member.application.dto.MemberResponse
import com.example.study.util.AcceptanceTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

class MemberAcceptanceTest : AcceptanceTest() {

    val EMAIL = "test@email.com"
    val PASSWORD = "password11@@"
    val NAME = "test"

    var 로그인토큰: TokenDto? = null
    var ACCESS_TOKEN: String? = null
    var 회원: MemberResponse? = null

    @BeforeEach
    fun setting() {
        회원 = 회원_생성_요청_반환(EMAIL, PASSWORD, NAME)
        로그인토큰 = 로그인_토큰(EMAIL, PASSWORD)
        ACCESS_TOKEN = 로그인토큰!!.accessToken
    }

    @DisplayName("회원 정보를 조회한다. - 내 정보의 경우")
    @Test
    fun getMemberInfo() {
        // when
        val response = 회원_정보_조회(ACCESS_TOKEN, 회원!!.id!!)

        // then
        assertThat(response!!.statusCode()).isEqualTo(HttpStatus.OK.value())
        assertThat(response!!.`as`(MemberResponse::class.java).email).isEqualTo(EMAIL)
    }

    @DisplayName("회원 정보를 조회 실패한다. - 다른 회원 정보의 경우")
    @Test
    fun getMemberInfoWhenOtherMember() {
        //given
        val 회원2 = 회원_생성_요청_반환("temp@email.com", PASSWORD, NAME)

        // when
        val response = 회원_정보_조회(ACCESS_TOKEN, 회원2!!.id!!)

        // then
        assertThat(response!!.statusCode()).isEqualTo(HttpStatus.OK.value())
        assertThat(response!!.`as`(MemberResponse::class.java).email).isEqualTo(EMAIL)
    }
}

