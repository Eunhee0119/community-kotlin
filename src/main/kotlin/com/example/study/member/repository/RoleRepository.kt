package com.example.study.member.repository

import com.example.study.member.domain.Member
import com.example.study.member.domain.Role
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RoleRepository : JpaRepository<Role?, Long?> {
}