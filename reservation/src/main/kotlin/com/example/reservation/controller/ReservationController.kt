package com.example.reservation.controller

import com.example.reservation.dto.ReservationRequestDTO
import com.example.reservation.dto.ReservationResponseDTO
import com.example.reservation.service.ReservationService
import com.example.response.Response
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/reservation")
class ReservationController(
    private val reservationService: ReservationService
) {

    @GetMapping("/{id}")
    suspend fun findReservation(@PathVariable id : Long) : Boolean {
        return true
    }

    @PostMapping
    suspend fun createReservation(@RequestBody reservationRequestDTO: ReservationRequestDTO) : Response<ReservationResponseDTO> {
        return Response.of(true, "예약에 성공하였습니다.", reservationService.reserve(reservationRequestDTO))
    }
}