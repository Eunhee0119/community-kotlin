package com.example.study.auth

import com.example.study.auth.token.JwtTokenProvider
import com.example.study.auth.token.TokenRequest
import com.example.study.auth.token.dto.RefreshTokenDto
import com.example.study.auth.token.dto.TokenDto
import com.example.study.common.exception.CustomException
import com.example.study.member.application.dto.MemberRequest
import com.example.study.member.application.dto.MemberResponse
import com.example.study.member.domain.Member
import com.example.study.member.repository.MemberRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit


@Service
class AuthService(
    private val memberRepository: MemberRepository,
    private val bCryptPasswordEncoder: PasswordEncoder,
    private val jwtTokenProvider: JwtTokenProvider,
    private val authenticationManager: AuthenticationManager,
    private val redisTemplate : RedisTemplate<String, String>
) {

    @Value("\${security.jwt.token.refresh-expire-length}")
    private lateinit var refreshExpireTime: Number

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


    fun signIn(tokenRequest: TokenRequest): TokenDto {
        return try {
            val authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    tokenRequest.email,
                    tokenRequest.password
                )
            )

            val accessToken =jwtTokenProvider.generateToken(authentication)!!
            val refreshToken = jwtTokenProvider.generateRefreshToken(authentication)!!
            val tokenDto = TokenDto(accessToken,refreshToken)

            redisTemplate.opsForValue().set(
                authentication.name,
                refreshToken,
                refreshExpireTime.toLong(),
                TimeUnit.MILLISECONDS
            )

            tokenDto
        } catch (e: AuthenticationException) {
            throw AuthenticationException("유효하지 않은 회원 정보 입니다.")
        }
    }

    fun regenerateToken(refreshTokenDto: RefreshTokenDto): TokenDto {
        val refreshToken: String = refreshTokenDto.refreshToken
        return try {

            if (!jwtTokenProvider.validateToken(refreshToken)) {
                throw
                CustomException("유효하지 않은 정보입니다.", HttpStatus.BAD_REQUEST)
            }

            val authentication: Authentication = jwtTokenProvider.getAuthentication(refreshToken)

            val validRefreshToken = redisTemplate.opsForValue()[authentication.name]
            if (validRefreshToken != refreshToken) {
                throw CustomException("토큰 값이 일치하지 않습니다.", HttpStatus.BAD_REQUEST)
            }

            // 토큰 재발행
            val newRefreshToken = jwtTokenProvider.generateRefreshToken(authentication)
            val tokenDto = TokenDto(
                jwtTokenProvider.generateToken(authentication),
                newRefreshToken!!
            )

            redisTemplate.opsForValue()[authentication.name, newRefreshToken, refreshExpireTime.toLong()] =
                TimeUnit.MILLISECONDS

            tokenDto
        } catch (e: AuthenticationException) {
            throw CustomException("유효하지 않은 정보입니다.", HttpStatus.BAD_REQUEST)
        }
    }
}
