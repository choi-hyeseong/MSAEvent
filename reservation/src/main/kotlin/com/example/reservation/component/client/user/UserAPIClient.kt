package com.example.reservation.component.client.user

import com.example.response.Response
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

//유저의 존재 여부를 체크

@FeignClient(name = "USER-API", fallbackFactory = UserFallbackFactory::class)
interface UserAPIClient {

    @GetMapping("/api/user/exist/{id}")
    fun check(@PathVariable id : Long) : Response<Boolean>
}