package com.example.study.post.application.dto

import com.example.study.member.application.dto.MemberResponse
import com.example.study.member.domain.Member
import com.example.study.post.domain.Post

class PostResponse(
    val id: Long?,
    val title: String,
    val content: String,
    val member: MemberResponse,
) {

    companion object{
        fun of(post: Post): PostResponse {
            return PostResponse(
                post.id,
                post.title,
                post.content,
                MemberResponse.of(post.member)
            )
        }
    }
}