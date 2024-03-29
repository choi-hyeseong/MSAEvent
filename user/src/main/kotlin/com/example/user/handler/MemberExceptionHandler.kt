package com.example.user.handler

import com.example.response.Response
import com.example.user.exception.MemberException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class MemberExceptionHandler {

    @ExceptionHandler(MemberException::class)
    fun handleMemberException(e : MemberException) : ResponseEntity<Response<Nothing>> {
        return ResponseEntity.badRequest().body(Response.of(false, e.message, null))
    }
}