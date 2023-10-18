package com.example.study.member

import com.example.study.auth.token.dto.TokenDto
import com.example.study.member.MemberSteps.로그인
import com.example.study.member.MemberSteps.토큰_재발급
import com.example.study.member.MemberSteps.회원_생성_요청
import com.example.study.util.AcceptanceTest
import io.restassured.response.ExtractableResponse
import io.restassured.response.Response
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

class AuthAcceptanceTest : AcceptanceTest() {
    val EMAIL = "test@email.com"
    val PASSWORD = "password11@@"
    val NICKNAME = "테스트계정"
    val NAME = "test"

    @DisplayName("회원가입을 한다.")
    @Test
    fun signUp() {
        // when
        val response: ExtractableResponse<Response?>? = 회원_생성_요청(EMAIL, PASSWORD, NICKNAME, NAME)

        // then
        assertThat(response!!.statusCode()).isEqualTo(HttpStatus.CREATED.value())
    }

    @DisplayName("회원가입에 실패한다. - 이미 등록된 이메일일 경우")
    @Test
    fun signUpExceptionWhenDuplicatedEmail() {
        회원_생성_요청(EMAIL, PASSWORD, NICKNAME, NAME)

        // when
        val response: ExtractableResponse<Response?>? = 회원_생성_요청(EMAIL, PASSWORD, NICKNAME, NAME)

        // then
        assertThat(response!!.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value())
    }

    @DisplayName("회원가입에 실패한다. - 이메일 유효성 검사 실패 시")
    @Test
    fun signUpExceptionWhenInvalidEmail() {
        // when
        val response: ExtractableResponse<Response?>? = 회원_생성_요청("test", PASSWORD, NICKNAME, NAME)

        // then
        assertThat(response!!.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value())
        assertThat(response.jsonPath().getString("message")).isEqualTo("올바른 형식의 이메일 주소여야 합니다")
    }

    @DisplayName("로그인을 한다.")
    @Test
    fun signIn() {
        회원_생성_요청(EMAIL, PASSWORD, NICKNAME, NAME)

        // when
        val response: ExtractableResponse<Response?> = 로그인(EMAIL, PASSWORD)

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value())

        val tokenDto = response.`as`(TokenDto::class.java)
        assertThat(tokenDto.accessToken).isNotEmpty
        assertThat(tokenDto.refreshToken).isNotEmpty
    }

    @DisplayName("로그인을 실패한다. - 회원 정보가 없는 경우")
    @Test
    fun signInWhenNoExistedUser() {

        // when
        val response: ExtractableResponse<Response?> = 로그인(EMAIL, PASSWORD)

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value())
    }

    @DisplayName("토큰 재발급을 한다.")
    @Test
    fun regenerateToken() {
        회원_생성_요청(EMAIL, PASSWORD, NICKNAME, NAME)
        val tokenDto = 로그인(EMAIL, PASSWORD).`as`(TokenDto::class.java)
        Thread.sleep(1000)

        // when
        val response = 토큰_재발급(tokenDto.refreshToken)

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value())

        val newTokenDto = response.`as`(TokenDto::class.java)
        assertThat(tokenDto.accessToken).isNotEqualTo(newTokenDto.accessToken)
        assertThat(tokenDto.refreshToken).isNotEqualTo(newTokenDto.refreshToken)
    }
}