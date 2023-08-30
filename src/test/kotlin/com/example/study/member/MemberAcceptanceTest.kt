package com.example.study.member

import com.example.study.member.MemberSteps.회원_생성_요청
import com.example.study.util.AcceptanceTest
import io.restassured.response.ExtractableResponse
import io.restassured.response.Response
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

class MemberAcceptanceTest : AcceptanceTest() {
    val EMAIL = "test@email.com"
    val PASSWORD = "password11@@"
    val NAME = "test"

    @DisplayName("회원가입을 한다.")
    @Test
    fun createMember() {
        // when
        val response: ExtractableResponse<Response?>? = 회원_생성_요청(EMAIL, PASSWORD, NAME)

        // then
        assertThat(response!!.statusCode()).isEqualTo(HttpStatus.CREATED.value())
    }

    @DisplayName("회원가입에 실패한다. - 이미 등록된 이메일일 경우")
    @Test
    fun createMemberExceptionWhenDuplicatedEmail() {
        // when
        회원_생성_요청(EMAIL, PASSWORD, NAME)
        val response: ExtractableResponse<Response?>? = 회원_생성_요청(EMAIL, PASSWORD, NAME)

        // then
        assertThat(response!!.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value())
    }

    @DisplayName("회원가입에 실패한다. - 이메일 유효성 검사 실패 시")
    @Test
    fun createMemberExceptionWhenInvalidEmail() {
        // when
        val response: ExtractableResponse<Response?>? = 회원_생성_요청("test", PASSWORD, NAME)

        // then
        assertThat(response!!.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value())
        assertThat(response.jsonPath().getString("message")).isEqualTo("올바른 형식의 이메일 주소여야 합니다")
    }
}