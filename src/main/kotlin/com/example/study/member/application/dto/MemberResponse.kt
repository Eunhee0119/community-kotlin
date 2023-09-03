package com.example.study.member.application.dto

import com.example.study.member.domain.Gender
import com.example.study.member.domain.Member
import com.example.study.member.domain.RoleType
import java.time.LocalDate

class MemberResponse(
    val id: Long?,
    val email: String?,
    val password: String?,
    val name: String?,
    val age: Int?,
    val birthDate: LocalDate?,
    val gender: Gender?,
) {
    companion object {
        fun of(member: Member): MemberResponse {
            return MemberResponse(
                member.id,
                member.email,
                member.password,
                member.name,
                member.age,
                member.birthDate,
                member.gender
            )
        }
    }
}
