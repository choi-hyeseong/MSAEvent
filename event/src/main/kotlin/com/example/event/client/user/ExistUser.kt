package com.example.event.client.user

import com.example.response.Response
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

//유저 존재하는지 여부 체크
@FeignClient(name = "USER-API")
interface ExistUser {

    @GetMapping("/api/user/exist/{id}")
    fun existUser(@PathVariable id : Long) : Boolean
}