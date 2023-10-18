package com.example.study.member.domain

import com.example.study.common.dto.EntityDate
import jakarta.persistence.*
import java.time.LocalDate

@Entity
class Member(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(unique = true, nullable = false, updatable = false)
    var email: String,

    @Column(nullable = false, length = 100)
    var password: String,

    @Column(nullable = false, length = 10)
    var nickname: String? = null,

    @Column(nullable = false, length = 10)
    var name: String? = null,

    var age: Int? = null,

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    var birthDate: LocalDate? = null,

    @Column(nullable = false, length = 5)
    @Enumerated(EnumType.STRING)
    var gender: Gender? = null
)

{
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
    var role: List<Role>? = null
}