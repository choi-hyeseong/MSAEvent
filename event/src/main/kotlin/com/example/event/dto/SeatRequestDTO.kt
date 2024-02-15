package com.example.event.dto

import com.example.event.entity.Event
import com.example.event.entity.Seat
import com.example.event.type.Status

class SeatRequestDTO (
    val name : String,
    val status : Status
) {

    fun toEntity(event : Event) = Seat(null, name, status, event)
}