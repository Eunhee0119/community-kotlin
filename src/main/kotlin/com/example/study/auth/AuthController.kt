package com.example.study.auth

import com.example.study.auth.token.TokenRequest
import com.example.study.auth.token.dto.RefreshTokenDto
import com.example.study.auth.token.dto.TokenDto
import com.example.study.member.application.dto.MemberRequest
import com.example.study.member.application.dto.MemberResponse
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.Valid
import lombok.RequiredArgsConstructor
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/signup")
    @Throws(Exception::class)
    fun signUp(@RequestBody @Valid request: MemberRequest): ResponseEntity<MemberResponse?>? {
        val member: MemberResponse = authService.signUp(request)
        return ResponseEntity.created(URI.create("/signUp/" + member.id)).body(member)
    }

    @PostMapping("/signin")
    fun signIn(@RequestBody request: TokenRequest, res: HttpServletResponse): ResponseEntity<TokenDto?> {
        val tokenDto: TokenDto = authService.signIn(request)
        val cookie = Cookie(
            "access_token",
            tokenDto.accessToken
        )
        cookie.path = "/"
        cookie.maxAge = Int.MAX_VALUE
        res.addCookie(cookie)

        val httpHeaders = HttpHeaders()
        httpHeaders.add("Authorization", "Bearer " + tokenDto.accessToken)
        return ResponseEntity.ok().headers(httpHeaders).body(tokenDto)
    }

    @PostMapping("/regeneratetoken")
    fun regenerateToken(@RequestBody refreshTokenDto: RefreshTokenDto): ResponseEntity<TokenDto?>? {
        return ResponseEntity.ok().body(authService.regenerateToken(refreshTokenDto))
    }
}