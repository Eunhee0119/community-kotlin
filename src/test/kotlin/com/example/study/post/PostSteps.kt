package com.example.study.post

import com.example.study.post.application.dto.PostResponse
import com.example.study.post.application.dto.PostUpdateRequest
import com.example.study.post.domain.Post
import io.restassured.RestAssured
import io.restassured.response.ExtractableResponse
import io.restassured.response.Response
import org.springframework.http.MediaType

object PostSteps {

    fun 게시판_조회(token: String?, i: Int): ExtractableResponse<Response>? {
        return RestAssured
            .given().log().all()
            .`when`().get("/posts")
            .then().log().all().extract()
    }

    fun 게시글_등록(token: String?, title: String, content: String): ExtractableResponse<Response> {
        var param : Map<String,String> = mapOf("title" to title, "content" to content)
        return RestAssured
            .given().log().all()
            .header("Authorization", "Bearer $token")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(param)
            .`when`().post("/posts")
            .then().log().all().extract()
    }


    fun 게시글_조회(token: String?, postId: Long?): ExtractableResponse<Response> {
        return RestAssured
            .given().log().all()
            .header("Authorization", "Bearer $token")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .`when`().get("/posts/$postId")
            .then().log().all().extract()
    }

    fun 게시글_수정(token: String?, post: PostUpdateRequest?): ExtractableResponse<Response> {
        return RestAssured
            .given().log().all()
            .header("Authorization", "Bearer $token")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(post)
            .`when`().put("/posts")
            .then().log().all().extract()
    }

}