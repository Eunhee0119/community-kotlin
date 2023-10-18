package com.example.study.post.repository

import com.example.study.post.application.dto.PostResponse
import com.example.study.post.domain.Post
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PostRepository : JpaRepository<Post?, Long?> , PostRepositoryCustom {
    fun save(post: PostResponse)

}
