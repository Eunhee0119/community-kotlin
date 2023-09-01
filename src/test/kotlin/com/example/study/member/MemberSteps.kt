package com.example.study.member

import com.example.study.member.application.dto.MemberRequest
import io.restassured.RestAssured
import io.restassured.response.ExtractableResponse
import io.restassured.response.Response
import org.springframework.http.MediaType

object MemberSteps {

    val AGE = 20
    val BIRTH_DATE = "1990-01-01"
    val GENDER = "MAN"
    val ROLE_TYPE = "ROLE_MEMBER"

    fun 회원_요청_정보(email: String, password: String, name: String): Map<String, Any> {
        return mapOf(
            "email" to email
            ,"password" to password
            ,"name" to name
            ,"age" to AGE
            , "birthDate" to BIRTH_DATE
            , "gender" to GENDER
            , "role" to ROLE_TYPE
        )
    }

    fun 회원_생성_요청(email: String, password: String, name: String): ExtractableResponse<Response?>? {
        val param = 회원_요청_정보(email, password, name)!!
        return RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(param)
            .`when`().post("/auth/signUp")
            .then().log().all().extract()
    }
}

