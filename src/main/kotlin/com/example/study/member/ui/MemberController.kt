package com.example.study.member.ui

import com.example.study.member.application.MemberService
import com.example.study.member.application.dto.MemberRequest
import com.example.study.member.application.dto.MemberResponse
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
class MemberController (
    private val memberService : MemberService) {

    @PostMapping("/members")
    fun createMember(@RequestBody @Valid request: MemberRequest?): ResponseEntity<Void?>? {
        val member: MemberResponse = memberService.createMember(request)
        return ResponseEntity.created(URI.create("/members/" + member.id)).build()
    }
}