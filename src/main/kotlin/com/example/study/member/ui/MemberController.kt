package com.example.study.member.ui

import com.example.study.common.dto.CustomUser
import com.example.study.member.application.MemberService
import com.example.study.member.application.dto.MemberResponse
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.security.Principal


@RestController("/members")
class MemberController (
    private val memberService : MemberService) {

    @GetMapping("/info")
    fun getMemberInfo(): ResponseEntity<MemberResponse?>? {

//        val id :Long = principal.userId
        val id = 1L
        val member: MemberResponse = memberService.findMember(id)
        return ResponseEntity.ok().body(member)
    }

}