package com.example.reservation.dto

import com.example.reservation.entity.Reservation

class ReservationRequestDTO(
    val userId : Long,
    val eventId : Long,
    val seatId : Long
) {
    fun toEntity() : Reservation = Reservation(null, userId, eventId, seatId)
}