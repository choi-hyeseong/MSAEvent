package com.example.reservation.component.client.event

import com.example.reservation.component.client.event.dto.SeatDTO
import com.example.reservation.component.client.event.dto.SeatStatus
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

//Seat의 존재 여부를 체크
@FeignClient(name = "EVENT-API")
interface EventAPIClient {

    @PostMapping("/api/event/check")
    fun checkSeat(@RequestBody seatDTO: SeatDTO) : SeatStatus?

    @PostMapping("/api/event/occupy")
    fun occupySeat(@RequestBody seatDTO: SeatDTO) : Boolean
}