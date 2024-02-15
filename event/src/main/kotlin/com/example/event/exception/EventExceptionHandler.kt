package com.example.event.exception

import com.example.response.Response
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class EventExceptionHandler {

    @ExceptionHandler(EventException::class)
    fun handleEventException(eventException: EventException) : Response<Long> {
        return Response.of(false, eventException.message, eventException.id)
    }
}