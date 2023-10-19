package com.example.study.post.application

import com.example.study.common.exception.CustomException
import com.example.study.member.domain.Member
import com.example.study.post.application.dto.*
import com.example.study.post.repository.PostRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service


@Suppress("SuspiciousCallableReferenceInLambda")
@Service
class PostService (
    val postRepository : PostRepository
        ){

    fun postContent(member: Member?, postRequest: PostRequest): PostResponse {
        val post = postRequest.toPost(member)
        postRepository.save(post)
        return PostResponse.of(post)
    }

    fun getPostList(): List<PostResponse> {
        return postRepository.findAll().map { PostResponse.of(it!!) }
    }

    fun getPostListWithCondition(cond: PostCondition): PostListDto? {
        return PostListDto.toDto(
            postRepository.findAllByCondition(cond)
        )
    }
    fun getPost(postId: Long): PostResponse {
        val post = postRepository.findById(postId).orElseThrow { RuntimeException("게시글을 찾을 수 없습니다.") }
        return PostResponse.of(post!!)
    }

    fun updatePost(memberId: Long, postRequest: PostUpdateRequest): PostResponse {
        val post = postRepository.findById(postRequest.id).orElseThrow{RuntimeException("게시글을 찾을 수 없습니다.")}

        if(post!!.member?.id != memberId) throw CustomException("권한이 없는 사용자입니다.",HttpStatus.BAD_REQUEST)

        post.title = postRequest.title
        post.content = postRequest.content
        postRepository.save(post)

        return PostResponse.of(post)
    }


}
