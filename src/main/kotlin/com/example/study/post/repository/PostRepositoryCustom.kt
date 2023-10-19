package com.example.study.post.repository

import com.example.study.post.application.dto.PostCondition
import com.example.study.post.application.dto.PostSimpleDto
import com.example.study.post.domain.Post
import org.springframework.data.domain.Page


interface PostRepositoryCustom  {
    fun findAllByCondition(cond: PostCondition): Page<PostSimpleDto>
}