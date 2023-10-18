package com.example.study.post.application.dto

import com.fasterxml.jackson.annotation.JsonFormat
import lombok.Data
import java.time.LocalDateTime

@Data
class PostSimpleDto {
    private val id: Long? = null
    private val title: String? = null
    private val nickname: String? = null

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private val createdAt: LocalDateTime? = null
}