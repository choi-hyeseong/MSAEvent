package com.example.event.dto

import com.example.event.entity.Event
import com.example.event.type.Status
import org.springframework.util.Assert

class EventResponseDTO(event: Event) {
    var name : String
    var status : Status
    var id : Long = 0
    var ownerId : Long = 0
    val seats : MutableList<SeatResponseDTO> = mutableListOf()

    init {
        Assert.notNull(event.id, "저장되지 않은 이벤트값입니다.")
        this.name = event.name
        this.status = event.status
        this.id = event.id!!
        this.ownerId = event.ownerId
        this.seats.addAll(event.seats.map { SeatResponseDTO(it) })
    }

}

fun Event.toResponseDTO() : EventResponseDTO = EventResponseDTO(this)