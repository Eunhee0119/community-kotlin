package com.example.study.common.dto

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User

class CustomUser(
    val userId: Long, email: String, password: String, role: Collection<GrantedAuthority>
) : User(email, password, role)