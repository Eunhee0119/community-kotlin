package com.example.study.auth

import com.example.study.member.domain.Member
import com.example.study.member.exception.NotFoundMemberException
import com.example.study.member.repository.MemberRepository
import jakarta.transaction.Transactional
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service


@Service
class CustomUserDetailsService(
    private val memberRepository: MemberRepository
) : UserDetailsService {

    @Transactional
    @Throws(NotFoundMemberException::class)
    override fun loadUserByUsername(email: String): UserDetails {
        val member: Member = memberRepository.findByEmail(email) ?: throw NotFoundMemberException()
        return User(member.email, member.password, member.role?.map { SimpleGrantedAuthority("ROLE_${it.roleType}") })
    }

}