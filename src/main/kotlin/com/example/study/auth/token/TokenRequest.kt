package com.example.study.auth.token

import com.example.study.member.domain.Member

class TokenRequest(
    var email: String?,
    var password: String?
)