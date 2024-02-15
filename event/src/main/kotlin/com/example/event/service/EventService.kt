package com.example.event.service

import com.example.event.dto.EventRequestDTO
import com.example.event.dto.EventResponseDTO
import com.example.event.dto.toResponseDTO
import com.example.event.exception.EventException
import com.example.event.repository.EventRepository
import com.example.event.repository.SeatRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class EventService(
    private val eventRepository: EventRepository,
    private val seatRepository: SeatRepository
) {

    @Transactional
    suspend fun createEvent(eventRequestDTO: EventRequestDTO) : EventResponseDTO {
        val event = eventRequestDTO.toEntity()
        return eventRepository.save(event).toResponseDTO()
    }

    @Transactional(readOnly = true)
    suspend fun findEvent(id : Long) : EventResponseDTO {
        return eventRepository.findById(id).orElseThrow { EventException("존재하지 않는 이벤트입니다.", id) }.toResponseDTO()
    }
}