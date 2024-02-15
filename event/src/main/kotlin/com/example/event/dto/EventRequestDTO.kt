package com.example.event.dto

import com.example.event.entity.Event
import com.example.event.type.Status

class EventRequestDTO (
    val name : String,
    val ownerId : Long,
    //초반에 생성하자마자 개장할지 말지 선택
    val status : Status,
    val seats : MutableList<SeatRequestDTO>
) {

    fun toEntity() : Event = Event(null, ownerId, name, status).also {
        it.seats.addAll(this.seats.map { seat -> seat.toEntity(it) })
    }
}