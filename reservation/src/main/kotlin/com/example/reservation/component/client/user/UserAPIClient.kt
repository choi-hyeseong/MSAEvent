package com.example.reservation.component.client.user

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

//유저의 존재 여부를 체크

@FeignClient(name = "USER-API")
interface UserAPIClient {

    @GetMapping("/api/user/exist/{id}")
    fun check(@PathVariable id : Long) : Boolean
}