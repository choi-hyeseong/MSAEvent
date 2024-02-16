package com.example.event.exception

import com.example.response.Response
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class EventExceptionHandler {

    @ExceptionHandler(EventException::class)
    fun handleEventException(eventException: EventException) : ResponseEntity<Response<Long>> {
        return ResponseEntity(Response.of(false, eventException.message, eventException.id), HttpStatus.BAD_REQUEST)
    }
}