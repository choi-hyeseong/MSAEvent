package com.example.event.service

import com.example.event.client.user.ExistUser
import com.example.event.dto.EventRequestDTO
import com.example.event.dto.EventResponseDTO
import com.example.event.dto.SeatDTO
import com.example.event.dto.toResponseDTO
import com.example.event.entity.Event
import com.example.event.exception.EventException
import com.example.event.repository.EventRepository
import com.example.event.repository.SeatRepository
import com.example.event.type.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.jvm.optionals.getOrNull

@Service
class EventService(
    private val eventRepository: EventRepository,
    private val seatRepository: SeatRepository,
    private val existUser: ExistUser
) {

    @Transactional
    suspend fun createEvent(eventRequestDTO: EventRequestDTO) : EventResponseDTO {
        val event = eventRequestDTO.toEntity()
        withContext(Dispatchers.IO) {
            if (!existUser.existUser(event.ownerId))
                throw EventException("존재하지 않는 유저의 id입니다.", event.ownerId)
        }
        return eventRepository.save(event).toResponseDTO()
    }

    @Transactional(readOnly = true)
    suspend fun findEvent(id : Long) : EventResponseDTO {
        return eventRepository.findById(id).orElseThrow { EventException("존재하지 않는 이벤트입니다.", id) }.toResponseDTO()
    }


    @Transactional
    suspend fun findEventEntityOrNull(id : Long) : Event? {
        return eventRepository.findById(id).getOrNull()
    }

    @Transactional(readOnly = true)
    suspend fun existSeat(seatDTO: SeatDTO) : Boolean {
        return seatRepository.existsByEventIdAndId(seatDTO.eventId, seatDTO.seatId)
    }

    @Transactional
    suspend fun occupySeat(seatDTO: SeatDTO) : Boolean {
        val event = findEventEntityOrNull(seatDTO.eventId) ?: return false // 이벤트가 존재하지 않을경우 false
        val seat = event.seats.find { it.id == seatDTO.seatId && it.status != Status.CLOSE } ?: return false // 좌석이 존재하지 않거나 이미 예약된경우 false
        seat.status = Status.CLOSE
        eventRepository.save(event)
        return true
    }
}