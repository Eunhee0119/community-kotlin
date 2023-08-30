package com.example.study.member.repository

import com.example.study.member.domain.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MemberRepository : JpaRepository<Member?, Long?> {
    fun findByEmail(email: String): Member?
}