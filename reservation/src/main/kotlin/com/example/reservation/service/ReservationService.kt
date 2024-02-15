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
            resolveStatus(eventAPIClient.checkSeat(seatDTO), reservation)
            if (!eventAPIClient.occupySeat(seatDTO))
                throw ReservationException("예약에 실패했습니다. 다시 시도해주세요.", reservation.seatId)
        }

        return reservationRepository.save(reservation).toResponseDTO()
    }

    private fun resolveStatus(status: SeatStatus?, reservation: Reservation) {
        when (status) {
            null -> throw ReservationException("존재하지 않는 좌석 정보입니다.", reservation.seatId)
            SeatStatus.CLOSE ->  throw ReservationException("이미 예약된 좌석입니다.", reservation.seatId)
            SeatStatus.DELETE -> throw ReservationException("예약할 수 없는 좌석입니다.", reservation.seatId)
            else -> {}
        }
    }
}