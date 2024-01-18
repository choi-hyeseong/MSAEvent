package com.example.user.repository

import com.example.user.entity.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long> {

    fun existsByUsername(username : String) : Boolean

    fun existsByNickname(nickname : String) : Boolean
}