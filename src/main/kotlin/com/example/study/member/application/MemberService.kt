package com.example.study.member.application

import com.example.study.member.application.dto.MemberRequest
import com.example.study.member.application.dto.MemberResponse
import com.example.study.member.domain.Member
import com.example.study.member.repository.MemberRepository
import org.springframework.stereotype.Service

@Service
class MemberService(
    private val memberRepository: MemberRepository
) {

    fun createMember(memberRequest: MemberRequest?): MemberResponse {
        val existedMember = memberRepository.findByEmail(memberRequest!!.email)
        if (existedMember != null) {
            throw IllegalArgumentException("이미 사용 중인 이메일입니다.")
        }
        val member: Member = memberRepository.save(memberRequest!!.toMember())
        return MemberResponse.of(member)
    }
}
