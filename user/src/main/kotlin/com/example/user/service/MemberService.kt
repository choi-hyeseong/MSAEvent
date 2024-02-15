package com.example.user.service

import com.example.user.dto.MemberRequestDTO
import com.example.user.dto.MemberResponseDTO
import com.example.user.entity.Member
import com.example.user.exception.MemberException
import com.example.user.repository.MemberRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberService(
    private val memberRepository: MemberRepository
) {

    @Transactional
    suspend fun register(memberRequestDTO: MemberRequestDTO): Long? {
        if (checkDuplicate(memberRequestDTO))
            throw MemberException("이미 존재하는 유저입니다.")
        return memberRepository.save(memberRequestDTO.toEntity()).id
    }

    //추후 validation으로 확장할 예정
    @Transactional
    suspend fun checkDuplicate(memberRequestDTO: MemberRequestDTO) : Boolean {
        return memberRepository.existsByNickname(memberRequestDTO.nickname) || memberRepository.existsByUsername(memberRequestDTO.username)
    }

    @Transactional
    suspend fun findEntityById(id: Long): Member {
        return memberRepository.findById(id).orElseThrow { MemberException("존재하지 않는 유저입니다.") }
    }

    @Transactional
    suspend fun findById(id: Long): MemberResponseDTO {
        return MemberResponseDTO(findEntityById(id))
    }

    @Transactional
    suspend fun existById(id : Long) : Boolean = memberRepository.existsById(id)


}