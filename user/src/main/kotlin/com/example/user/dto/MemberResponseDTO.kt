package com.example.user.dto

import com.example.user.entity.Member

class MemberResponseDTO(
    val id : Long,
    val username : String,
    val password : String,
    val nickname : String
) {
    constructor(member: Member) : this(member.id!!, member.username, member.password, member.nickname)
}