package com.example.study.post.domain

import com.example.study.common.dto.EntityDate
import com.example.study.member.domain.Member
import jakarta.persistence.*

@Entity
class Post (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    var title: String,

    @Column(nullable = false)
    @Lob
    var content: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    var member: Member? = null,

) :EntityDate()


