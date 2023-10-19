package com.example.study.post.application.dto

import com.fasterxml.jackson.annotation.JsonFormat
import lombok.Data
import java.time.LocalDateTime

@Data
class PostSimpleDto (
    val id: Long? = null,
    val title: String? = null,
    val nickname: String? = null,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    val createdAt: LocalDateTime? = null
)