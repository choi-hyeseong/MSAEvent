package com.example.reservation.component.client.event

import com.example.reservation.component.client.event.dto.SeatDTO
import com.example.response.Response
import feign.FeignException
import org.springframework.cloud.openfeign.FallbackFactory
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

//Seat의 존재 여부를 체크
@FeignClient(name = "EVENT-API", fallbackFactory = EventFallbackFactory::class)
interface EventAPIClient {
    /**
    * Check와 occupy를 한 호출에 진행하는 api.
     */
    @PostMapping("/api/event/occupy")
    fun occupySeat(@RequestBody seatDTO: SeatDTO) : Response<SeatDTO>

}