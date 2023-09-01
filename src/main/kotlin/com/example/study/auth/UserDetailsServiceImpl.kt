package com.example.study.auth

import com.example.study.member.domain.Member
import com.example.study.member.exception.NotFoundMemberException
import com.example.study.member.repository.MemberRepository
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service


@Service
class UserDetailsServiceImpl(
    private val memberRepository: MemberRepository
) : UserDetailsService {

    @Throws(NotFoundMemberException::class)
    override fun loadUserByUsername(email: String): UserDetails {
        println("email in loadUserByUsername = $email")
        val member: Member = memberRepository.findByEmail(email) ?: throw NotFoundMemberException()
        val grantedAuthorities: Set<GrantedAuthority> = HashSet()
        return User(member.email, member.password, grantedAuthorities)
    }
}