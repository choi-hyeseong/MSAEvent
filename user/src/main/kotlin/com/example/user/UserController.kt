package com.example.user

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController {

    @GetMapping("/api/user/")
    fun handleCheck() : String {
        println("Handle Request")
        return "Response Server"
    }

}