package com.example.study.auth

import com.example.study.auth.token.JwtTokenProvider
import com.example.study.auth.token.TokenDto
import com.example.study.auth.token.TokenRequest
import com.example.study.member.application.dto.MemberRequest
import com.example.study.member.application.dto.MemberResponse
import com.example.study.member.domain.Member
import com.example.study.member.repository.MemberRepository
import jakarta.transaction.Transactional
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val memberRepository: MemberRepository,
    private val bCryptPasswordEncoder: PasswordEncoder,
    private val jwtTokenProvider: JwtTokenProvider,
    private val authenticationManager: AuthenticationManager
) {

    @Transactional
    @Throws(Exception::class)
    fun signUp(memberRequest: MemberRequest): MemberResponse {
        val existedMember = memberRepository.findByEmail(memberRequest!!.email)
        if (existedMember != null) {
            throw IllegalArgumentException("이미 사용 중인 이메일입니다.")
        }

        val encodePassword = bCryptPasswordEncoder.encode(memberRequest.password)
        val member: Member = memberRepository.save(memberRequest!!.toMember(encodePassword))
        return MemberResponse.of(member)
    }


    fun signIn(tokenRequest: TokenRequest): ResponseEntity<TokenDto?> {
        return try {
            val authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    tokenRequest.email,
                    tokenRequest.password
                )
            )
            val tokenDto = TokenDto(jwtTokenProvider.generateToken(authentication)!!)
            val httpHeaders = HttpHeaders()
            httpHeaders.add("Authorization", "Bearer " + tokenDto.access_token)
            ResponseEntity(tokenDto, httpHeaders, HttpStatus.OK)
        } catch (e: AuthenticationException) {
            throw AuthenticationException("유효하지 않은 회원 정보 입니다.")
        }
    }
}
