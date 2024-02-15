package com.example.event.dto

import com.example.event.type.Status

class SeatSaveDTO (
    val eventId : Long,
    val seatId : Long,
    val status : Status
)