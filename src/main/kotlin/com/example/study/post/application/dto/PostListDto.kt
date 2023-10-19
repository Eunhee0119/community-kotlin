package com.example.study.post.application.dto

import lombok.Data
import org.springframework.data.domain.Page


@Data
class PostListDto(
    val totalElements: Long? = null,
    val totalPages: Int? = null,
    val hasNext: Boolean = false,
    val postList: List<PostSimpleDto>? = null
) {

    companion object {
        fun toDto(page: Page<PostSimpleDto>): PostListDto {
            return PostListDto(page.totalElements, page.totalPages, page.hasNext(), page.content)
        }
    }
}