package com.example.study.post.ui

import com.example.study.common.dto.CustomUser
import com.example.study.member.application.MemberService
import com.example.study.post.application.dto.PostRequest
import com.example.study.post.application.PostService
import com.example.study.post.application.dto.PostResponse
import com.example.study.post.application.dto.PostUpdateRequest
import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.net.URI
import java.util.*


@RestController
@RequestMapping("/posts")
class PostController(
    val postService: PostService,
    val memberService: MemberService
) {
    private val logger = LoggerFactory.getLogger(PostController::class.java)

    @GetMapping
    fun getBoards(): ResponseEntity<List<PostResponse>> {
        val postList = postService.getPostList()
        return ResponseEntity.ok().body(postList)
    }

    @GetMapping
    @RequestMapping("/{id}")
    fun getPost(@PathVariable id : Long): ResponseEntity<PostResponse> {
        val post = postService.getPost(id)
        return ResponseEntity.ok().body(post)
    }

    @PostMapping
    fun postContent(@AuthenticationPrincipal principal: CustomUser, @RequestBody @Valid postRequest: PostRequest): ResponseEntity<PostResponse> {
        val member = memberService.findMember(principal.userId)
        val post = postService.postContent(member, postRequest)
        return ResponseEntity.created(URI.create("/post/" + post.id)).body(post)
    }

    @PutMapping
    fun updatePost(@AuthenticationPrincipal principal: CustomUser, @RequestBody @Valid postRequest: PostUpdateRequest): ResponseEntity<PostResponse> {
        val post = postService.updatePost(principal.userId, postRequest)
        logger.error(" id => {}", postRequest.id)
        return ResponseEntity.ok().body(post)
    }
}