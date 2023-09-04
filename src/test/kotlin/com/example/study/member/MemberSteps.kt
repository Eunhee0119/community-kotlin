package com.example.study.member

import com.example.study.auth.token.dto.TokenDto
import com.example.study.member.application.dto.MemberResponse
import io.restassured.RestAssured
import io.restassured.response.ExtractableResponse
import io.restassured.response.Response
import org.springframework.http.MediaType

object MemberSteps {

    val AGE = 20
    val BIRTH_DATE = "1990-01-01"
    val GENDER = "MAN"

    fun 회원_요청_정보(email: String, password: String, name: String): Map<String, Any> {
        return mapOf(
            "email" to email,
            "password" to password,
            "name" to name,
            "age" to AGE,
            "birthDate" to BIRTH_DATE,
            "gender" to GENDER
        )
    }

    fun 회원_생성_요청(email: String, password: String, name: String): ExtractableResponse<Response?> {
        val param = 회원_요청_정보(email, password, name)!!
        return RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(param)
            .`when`().post("/auth/signup")
            .then().log().all().extract()
    }

    fun 회원_생성_요청_반환(email: String, password: String, name: String): MemberResponse? {
        return 회원_생성_요청(email, password, name)
            .`as`(MemberResponse::class.java)
    }

    fun 로그인(email: String, password: String): ExtractableResponse<Response?> {
        val param = mapOf(
            "email" to email, "password" to password
        )
        return RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(param)
            .`when`().post("/auth/signin")
            .then().log().all().extract()
    }

    fun 로그인_토큰(email: String, password: String): TokenDto? {
        return 로그인(email, password)
            .`as`(TokenDto::class.java)
    }

    fun 토큰_재발급(refreshToken: String): ExtractableResponse<Response?> {
        val param = mapOf(
            "refreshToken" to refreshToken
        )
        return RestAssured
            .given().log().all()
            .header("Authorization", "Bearer $refreshToken")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(param)
            .`when`().post("/auth/regeneratetoken")
            .then().log().all().extract()
    }

    fun 회원_정보_조회(TOKEN: String?, id: Long): ExtractableResponse<Response?> {
        return RestAssured
            .given().log().all()
            .header("Authorization", "Bearer $TOKEN")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .`when`().get("/members/info")
            .then().log().all().extract()
    }
}

