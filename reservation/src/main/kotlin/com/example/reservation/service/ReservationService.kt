package com.example.reservation.service

import com.example.reservation.component.client.event.EventAPIClient
import com.example.reservation.component.client.event.dto.SeatDTO
import com.example.reservation.component.client.event.dto.SeatStatus
import com.example.reservation.component.client.user.UserAPIClient
import com.example.reservation.dto.ReservationRequestDTO
import com.example.reservation.dto.ReservationResponseDTO
import com.example.reservation.dto.toResponseDTO
import com.example.reservation.entity.Reservation
import com.example.reservation.exception.ReservationException
import com.example.reservation.repository.ReservationRepository
import com.example.response.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ReservationService(
    private val reservationRepository: ReservationRepository,
    private val eventAPIClient: EventAPIClient,
    private val userAPIClient: UserAPIClient,
    ) {

    @Transactional
    suspend fun reserve(requestDTO: ReservationRequestDTO) : Response<ReservationResponseDTO> {
        val reservation = requestDTO.toEntity()
        // IO 디스패처 실행
        return withContext(Dispatchers.IO) {
            if (!userAPIClient.check(reservation.userId))
                throw ReservationException("존재하지 않는 유저입니다.", reservation.userId)
            // check랑 request 동시 호출
            val responseOccupy = eventAPIClient.occupySeat(SeatDTO(reservation.eventId, reservation.seatId))
            if (!responseOccupy.success)
                throw ReservationException(responseOccupy.message ?: "예약 API가 응답하지 않습니다.", reservation.seatId)
            else
                Response.of(true, responseOccupy.message, reservationRepository.save(reservation).toResponseDTO())
        }
    }

}