package com.example.study.member.application

import com.example.study.member.application.dto.MemberRequest
import com.example.study.member.application.dto.MemberResponse
import com.example.study.member.exception.NotFoundMemberException
import com.example.study.member.repository.MemberRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val bCryptPasswordEncoder: PasswordEncoder
) {
    fun findMember(id: Long): MemberResponse {
        val member = memberRepository.findById(id).orElseThrow { NotFoundMemberException() }
        return MemberResponse.of(member!!)
    }

    fun updateMemberInfo(memberRequest: MemberRequest): MemberResponse {
        val encodedPassword: String = bCryptPasswordEncoder.encode(memberRequest.password)
        val member = memberRequest.toMember(encodedPassword)
        memberRepository.save(member)
        return MemberResponse.of(member)
    }
}
