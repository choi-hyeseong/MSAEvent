package com.example.event.service

import com.example.event.component.client.user.UserAPIClient
import com.example.event.component.kafka.producer.KafkaProducer
import com.example.event.dto.*
import com.example.event.entity.Event
import com.example.event.entity.Seat
import com.example.event.exception.EventException
import com.example.event.repository.EventRepository
import com.example.event.repository.SeatRepository
import com.example.event.type.Status
import com.example.response.Response
import com.example.response.logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.jvm.optionals.getOrNull

@Service
class EventService(
    private val eventRepository: EventRepository,
    private val seatRepository: SeatRepository,
    private val userClient: UserAPIClient,
    private val kafkaProducer: KafkaProducer
) {

    private val log = logger()

    @Transactional
    suspend fun createEvent(eventRequestDTO: EventRequestDTO) : EventResponseDTO {
        val event = eventRequestDTO.toEntity()
        withContext(Dispatchers.IO) {
            if (!userClient.existUser(event.ownerId).success)
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

    @Transactional
    suspend fun findSeatWithEventId(eventId: Long, seatId: Long) : Seat? {
        return seatRepository.findByEventIdAndId(eventId, seatId).getOrNull()
    }

    @Transactional
    suspend fun occupySeat(seatDTO: SeatDTO) : SeatDTO {
        val seat = findSeatWithEventId(seatDTO.eventId, seatDTO.seatId)
        if (seat == null)
            throw EventException("존재하지 않는 좌석 정보 입니다. id : ${seatDTO.seatId}", null)
        if (seat.status != Status.OPEN)
            throw EventException("예약할 수 없는 좌석입니다. id : ${seat.id}", null)

        val seatSaveDTO = SeatSaveDTO(seatDTO.eventId, seatDTO.seatId, Status.CLOSE) //Event와 유사한 DTO
        kafkaProducer.sendOccupyRequest(seatSaveDTO)
        return seatDTO //delete boolean (check랑 통합)
    }

    @Transactional
    suspend fun saveSeat(seatSaveDTO: SeatSaveDTO) {
        val seat = findSeatWithEventId(seatSaveDTO.eventId, seatSaveDTO.seatId)
        if (Status.OPEN == seat?.status) {
            seat.status = seatSaveDTO.status
            seatRepository.save(seat)
        }
        else
            log.warn("해당 좌석 정보를 저장할 수 없습니다. eventID : {}, seatID : {}, status : {}", seatSaveDTO.eventId, seatSaveDTO.seatId, seat?.status)
    }
}