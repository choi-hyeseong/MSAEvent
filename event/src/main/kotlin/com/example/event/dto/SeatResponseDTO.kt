package com.example.event.dto

import com.example.event.entity.Seat
import com.example.event.type.Status
import org.springframework.util.Assert

class SeatResponseDTO (seat: Seat) {
    var id : Long = 0 //seat의 고유 id
    var name : String //seat의 좌석 이름
    var status : Status //seat의 상태

    init {
        Assert.notNull(seat.id, "저장되지 않은 좌석의 id입니다.")
        this.id = seat.id!!
        this.name = seat.name
        this.status = seat.status
    }
}