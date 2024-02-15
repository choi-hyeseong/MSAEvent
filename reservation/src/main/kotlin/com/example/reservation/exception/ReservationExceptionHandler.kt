package com.example.reservation.exception

import com.example.response.Response
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ReservationExceptionHandler {

    @ExceptionHandler(ReservationException::class)
    fun handleReservationException(e : ReservationException) : Response<Long> {
        return Response.of(false, e.message, e.id)
    }
}