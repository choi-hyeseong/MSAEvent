package com.example.user.controller

import com.example.response.Response
import com.example.user.dto.MemberRequestDTO
import com.example.user.dto.MemberResponseDTO
import com.example.user.service.MemberService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/user")
class MemberController(private val memberService: MemberService) {

    @GetMapping("/health")
    fun handleCheck() : String {
        println("Handle Request")
        return "Response Server"
    }

    @PostMapping("/register")
    suspend fun register(@RequestBody memberRequestDTO: MemberRequestDTO) : Response<MemberResponseDTO> {
        val response = memberService.register(memberRequestDTO)
        return Response.of(true, "회원가입이 완료 되었습니다. 유저 ID : $response", null)
    }

    @GetMapping("/{id}")
    suspend fun getUser(@PathVariable(value = "id") id : Long) : Response<MemberResponseDTO> {
        return Response.of(true, null,memberService.findById(id))
    }

}