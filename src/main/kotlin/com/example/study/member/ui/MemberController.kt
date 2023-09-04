package com.example.study.member.ui

import com.example.study.common.dto.CustomUser
import com.example.study.member.application.MemberService
import com.example.study.member.application.dto.MemberRequest
import com.example.study.member.application.dto.MemberResponse
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/members")
class MemberController(
    private val memberService: MemberService
) {

    @GetMapping("/info")
    fun getMemberInfo(@AuthenticationPrincipal principal: CustomUser): ResponseEntity<MemberResponse?>? {
        val id: Long = principal.userId
        val member: MemberResponse = memberService.findMember(id)
        return ResponseEntity.ok().body(member)
    }

    @PutMapping("/info")
    fun updateMemberInfo(
        @RequestBody @Valid memberRequest: MemberRequest,
        @AuthenticationPrincipal principal: CustomUser
    ): ResponseEntity<MemberResponse> {
        memberRequest.id = principal.userId
        val member: MemberResponse = memberService.updateMemberInfo(memberRequest)
        return ResponseEntity.ok().body(member)
    }
}