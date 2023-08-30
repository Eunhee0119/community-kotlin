package com.example.study.member.domain

import jakarta.persistence.*
import java.time.LocalDate

@Entity
class Member{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(unique = true, nullable = false , updatable = false)
    var email: String? = null

    @Column(nullable = false, length = 100)
    var password: String? = null

    @Column(nullable = false, length = 10)
    var name :String? = null

    var age: Int? = null

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    var birthDate : LocalDate? = null

    @Column(nullable = false, length = 5)
    @Enumerated(EnumType.STRING)
    var gender : Gender? = null

    @Column(nullable = false, length = 12)
    @Enumerated(EnumType.STRING)
    var role: RoleType? = null


    constructor(
        id: Long?,
        email: String?,
        password: String?,
        name: String?,
        age: Int?,
        birthDate: LocalDate?,
        gender: Gender?,
        role: RoleType?
    ) {
        this.id = id
        this.email = email
        this.password = password
        this.name = name
        this.age = age
        this.birthDate = birthDate
        this.gender = gender
        this.role = role
    }
}