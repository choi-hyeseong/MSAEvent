package com.example.event.repository

import com.example.event.entity.Seat
import com.example.event.type.Status
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface SeatRepository : JpaRepository<Seat, Long> {

    fun findByEventIdAndId(eventId : Long, seatId : Long) : Optional<Seat>
}