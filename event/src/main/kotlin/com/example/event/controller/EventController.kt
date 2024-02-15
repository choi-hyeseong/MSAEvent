package com.example.event.controller

import com.example.event.dto.EventRequestDTO
import com.example.event.dto.EventResponseDTO
import com.example.event.dto.SeatDTO
import com.example.event.service.EventService
import com.example.event.type.Status
import com.example.response.Response
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/event")
class EventController(
    private val eventService: EventService
) {

    /**
     * Event 생성 api /api/event (POST)
     */
    @PostMapping
    suspend fun createEvent(@RequestBody eventRequestDTO: EventRequestDTO) : Response<EventResponseDTO> {
        return Response.of(true, "이벤트가 생성되었습니다.", eventService.createEvent(eventRequestDTO))
    }

    /**
     * Event 정보 확인 /api/event/{id} (GET)
     */
    @GetMapping("/{id}")
    suspend fun getEventInfo(@PathVariable id : Long) : Response<EventResponseDTO> {
        return Response.of(true, null, eventService.findEvent(id))
    }

    @PostMapping("/check")
    suspend fun checkSeat(@RequestBody seatDTO: SeatDTO) : Status? {
        return eventService.findSeatStatus(seatDTO.eventId, seatDTO.seatId)
    }


    @PostMapping("/occupy")
    suspend fun occupySeat(@RequestBody seatDTO: SeatDTO) : Boolean {
        return eventService.occupySeat(seatDTO.eventId, seatDTO.seatId)
    }

}