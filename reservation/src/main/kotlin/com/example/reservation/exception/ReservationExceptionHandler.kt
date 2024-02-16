package com.example.reservation.exception

import com.example.response.Response
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ReservationExceptionHandler {

    @ExceptionHandler(ReservationException::class)
    fun handleReservationException(e : ReservationException) : ResponseEntity<Response<Long>> {
        return ResponseEntity(Response.of(false, e.message, e.id), HttpStatus.BAD_REQUEST)
    }
}