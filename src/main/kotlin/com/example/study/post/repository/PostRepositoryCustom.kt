package com.example.study.post.repository

import com.example.study.post.application.dto.PostCondition
import com.example.study.post.domain.Post

interface PostRepositoryCustom  {
    fun search(condition : PostCondition):List<Post>
}