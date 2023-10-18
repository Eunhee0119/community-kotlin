package com.example.study.post.application.dto

import com.example.study.member.domain.Member
import com.example.study.post.domain.Post
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank

class PostRequest (
    var id : Long?,

    @field: NotBlank
    @JsonProperty("title")
    private val _title: String?,

    @field: NotBlank
    @JsonProperty("content")
    private val _content: String?,


        ){


    val title: String
        get() = _title!!

    val content: String
        get() = _content!!


    fun toPost(member:Member?): Post =
        Post(id,title,content,member)

}