package com.example.study.post.application.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

class PostUpdateRequest (
    @field: NotNull
    @JsonProperty("id")
    private val _id : Long,

    @field: NotBlank
    @JsonProperty("title")
    private val _title: String?,

    @field: NotBlank
    @JsonProperty("content")
    private val _content: String?,

    ){

    val id : Long
        get() = _id!!

    val title: String
        get() = _title!!

    val content: String
        get() = _content!!

}