package com.example.study.member.domain

import jakarta.persistence.*

@Entity
class Role(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false, length = 12)
    @Enumerated(EnumType.STRING)
    var roleType: RoleType? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = ForeignKey(name = "fk_member_role_member_id"))
    var member: Member? = null
)