package com.example.study.member.application.dto

import com.example.study.common.annotation.ValidEnum
import com.example.study.member.domain.Gender
import com.example.study.member.domain.Member
import com.example.study.member.domain.RoleType
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class MemberRequest(
    var id: Long?,

    @field : NotBlank
    @field:Email
    @JsonProperty("email")
    private val _email: String?,

    @field : NotBlank
    @field:Pattern(
        regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#\$%^&*])[a-zA-Z0-9!@#\$%^&*]{8,20}\$",
        message = "영문, 숫자, 특수문자를 포함한 8~20자리로 입력해주세요"
    )
    @JsonProperty("password")
    private val _password: String?,

    @field : NotBlank
    @JsonProperty("name")
    private val _name: String?,

    @field : NotNull
    @Digits(fraction = 0, integer = 10, message = "연령을 확인해주세요.")
    @JsonProperty("age")
    private val _age: Int?,

    @field : NotBlank
    @field:Pattern(
        regexp = "^([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))$",
        message = "날짜형식(YYYY-MM-DD)을 확인해주세요"
    )
    @JsonProperty("birthDate")
    private val _birthDate: String?,

    @field : NotBlank
    @field:ValidEnum(enumClass = Gender::class, message = "MAN 이나 WOMAN 중 하나를 선택해주세요.")
    @JsonProperty("gender")
    private val _gender: String?,

) {
    val email: String
        get() = _email!!
    val password: String
        get() = _password!!
    val name: String
        get() = _name!!
    val age: Int
        get() = _age!!
    val birthDate: LocalDate
        get() = _birthDate!!.toLocalDate()
    val gender: Gender
        get() = Gender.valueOf(_gender!!)

    private fun String.toLocalDate(): LocalDate =
        LocalDate.parse(this, DateTimeFormatter.ofPattern("yyyy-MM-dd"))

    fun toMember(encodePassword: String): Member =
        Member(id, email, encodePassword, name, age, birthDate, gender)

}
