package com.example.reservation.component.client.event

import com.example.reservation.component.client.AbstractFallbackFactory
import com.example.reservation.component.client.event.dto.SeatDTO
import com.example.reservation.exception.ReservationException
import com.example.response.Response
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import feign.FeignException
import org.springframework.cloud.openfeign.FallbackFactory
import org.springframework.stereotype.Component
import java.io.ByteArrayInputStream
import kotlin.jvm.optionals.getOrNull

@Component
class EventFallbackFactory(objectMapper: ObjectMapper) : AbstractFallbackFactory<EventFallback>(objectMapper) {
    override fun provideFallback(): EventFallback {
        return EventFallback()
    }
}

class EventFallback : EventAPIClient {

    override fun occupySeat(seatDTO: SeatDTO): Response<SeatDTO> {
        return Response.of(false, "좌석 정보 API서버가 응답하지 않습니다.", null)
    }

}