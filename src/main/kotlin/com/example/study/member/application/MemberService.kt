package com.example.study.member.application

import com.example.study.member.application.dto.MemberRequest
import com.example.study.member.application.dto.MemberResponse
import com.example.study.member.domain.Member
import com.example.study.member.repository.MemberRepository
import org.springframework.stereotype.Service

@Service
class MemberService(
    private val memberRepository: MemberRepository
)
