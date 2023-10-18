package com.example.study.member.unit.fixture

import com.example.study.member.domain.Gender
import com.example.study.member.domain.Member
import org.springframework.test.util.ReflectionTestUtils
import java.time.LocalDate

class MemberFixture {
    companion object {
        private var id = 1L

        fun 회원_등록(
            email: String,
            password: String,
            nickname: String,
            name: String,
            age: Int,
            birthDate: LocalDate,
            gender: Gender
        ): Member {
            val member = Member(null, email, password, nickname, name, age, birthDate, gender)
            ReflectionTestUtils.setField(member, "id", id++)
            return member
        }

    }
}