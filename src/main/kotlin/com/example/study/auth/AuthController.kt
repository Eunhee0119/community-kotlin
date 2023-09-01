package com.example.study.auth

import com.example.study.auth.token.TokenDto
import com.example.study.auth.token.TokenRequest
import com.example.study.member.application.dto.MemberRequest
import com.example.study.member.application.dto.MemberResponse
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.Valid
import lombok.RequiredArgsConstructor
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
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

    @PostMapping("/signUp")
    @Throws(Exception::class)
    fun signUp(@RequestBody @Valid request: MemberRequest): ResponseEntity<MemberResponse?>? {
        val member: MemberResponse = authService.signUp(request)
        return ResponseEntity.created(URI.create("/members/" + member.id)).build()
    }

    @PostMapping("/signIn")
    fun signIn(@Validated request: TokenRequest, res: HttpServletResponse): String {
        val tokenDtoResponseEntity: ResponseEntity<TokenDto?> = authService.signIn(request)
        val cookie = Cookie(
            "access_token",
            tokenDtoResponseEntity.body!!.accessToken
        )
        cookie.path = "/"
        cookie.maxAge = Int.MAX_VALUE
        res.addCookie(cookie)
        return "redirect:/index"
    }
}