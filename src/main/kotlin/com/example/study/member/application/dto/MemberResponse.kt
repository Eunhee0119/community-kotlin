package com.example.study.member.application.dto

import com.example.study.member.domain.Gender
import com.example.study.member.domain.Member
import com.example.study.member.domain.RoleType
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MemberResponse(
    val id: Long?,
    val email: String?,
    val password: String?,
    val name: String?,
    val age: Int?,
    val birthDate: String?,
    val gender: String?,
) {

    companion object {
        private fun LocalDate.toFarmatDate(): String =
            this.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

        fun of(member: Member): MemberResponse {
            return MemberResponse(
                member.id,
                member.email,
                member.password,
                member.name,
                member.age,
                member.birthDate?.toFarmatDate(),
                member.gender?.desc
            )
        }
    }
}
