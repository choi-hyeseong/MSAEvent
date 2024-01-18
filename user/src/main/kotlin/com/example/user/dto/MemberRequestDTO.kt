package com.example.user.dto

import com.example.user.entity.Member

class MemberRequestDTO(
    val username : String,
    val password : String,
    val nickname : String
) {

    fun toEntity() : Member = Member(null, username, password, nickname)
}