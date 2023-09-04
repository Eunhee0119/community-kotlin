package com.example.study.member.application

import com.example.study.member.application.dto.MemberResponse
import com.example.study.member.exception.NotFoundMemberException
import com.example.study.member.repository.MemberRepository
import org.springframework.stereotype.Service

@Service
class MemberService(
    private val memberRepository: MemberRepository
) {
    fun findMember(id: Long): MemberResponse {
        val member = memberRepository.findById(id).orElseThrow { NotFoundMemberException() }
        return MemberResponse.of(member!!)
    }
}
