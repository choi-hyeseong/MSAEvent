package com.example.reservation.dto

import com.example.reservation.entity.Reservation
import org.springframework.util.Assert

class ReservationResponseDTO(reservation: Reservation) {

    var id : Long = 0
    var userId : Long = 0
    var seatId : Long = 0
    var eventId : Long = 0

    init {
        Assert.notNull(reservation.id, "예약의 id는 null이 와선 안됩니다.")
        this.id = reservation.id!!
        this.userId = reservation.userId
        this.seatId = reservation.seatId
        this.eventId = reservation.eventId
    }
}

//기능의 분리. 엔티티에 선언하는것 보다 dto에 선언해두고 확장함수로 구현
fun Reservation.toResponseDTO() : ReservationResponseDTO = ReservationResponseDTO(this)