package com.example.reservation.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class Reservation (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long?,

    @Column
    val userId : Long,

    @Column
    val eventId : Long, //이벤트의 ID, 좌석의 ID로도 참조가 가능하나, 좀더 명확하게 하기 위함

    @Column
    val seatId : Long //좌석의 고유 ID
)