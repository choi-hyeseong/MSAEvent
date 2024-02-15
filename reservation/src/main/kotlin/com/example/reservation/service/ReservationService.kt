package com.example.reservation.service

import com.example.reservation.component.client.event.EventAPIClient
import com.example.reservation.component.client.event.dto.SeatDTO
import com.example.reservation.component.client.user.UserAPIClient
import com.example.reservation.dto.ReservationRequestDTO
import com.example.reservation.dto.ReservationResponseDTO
import com.example.reservation.dto.toResponseDTO
import com.example.reservation.exception.ReservationException
import com.example.reservation.repository.ReservationRepository
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
    suspend fun reserve(requestDTO: ReservationRequestDTO) : ReservationResponseDTO {
        val reservation = requestDTO.toEntity()
        // IO 디스패처 실행
        withContext(Dispatchers.IO) {
            if (!userAPIClient.check(reservation.userId))
                throw ReservationException("존재하지 않는 유저입니다.", reservation.userId)

            val seatDTO = SeatDTO(reservation.eventId, reservation.seatId)
            if (!eventAPIClient.checkSeat(seatDTO))
                throw ReservationException("존재하지 않는 좌석 정보입니다.", reservation.seatId)
            if (!eventAPIClient.occupySeat(seatDTO))
                throw ReservationException("예약에 실패했습니다. 다시 시도해주세요.", reservation.seatId)
        }

        return reservationRepository.save(reservation).toResponseDTO()
    }
}