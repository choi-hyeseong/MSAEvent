package com.example.reservation.component.client.event

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
class EventFallbackFactory(val objectMapper: ObjectMapper) : FallbackFactory<EventFallback> {

    override fun create(cause: Throwable?): EventFallback {
        val jsonExceptionMessage = deserialize(cause)
        if (jsonExceptionMessage != null)
            throw ReservationException(jsonExceptionMessage, null)
        return EventFallback()
    }

    // Response<T> 형식으로 예외가 발생한경우 메시지 반환.
    private fun deserialize(cause: Throwable?) : String? {
        if (cause !is FeignException)
            return null

        val response = cause.responseBody().getOrNull() ?: return null
        val deserializeResponse = kotlin.runCatching {
            objectMapper.readValue(response.array().toString(), object : TypeReference<Response<Nothing>>() {})
        }.getOrNull()
        return deserializeResponse?.message
    }

}
class EventFallback : EventAPIClient {

    override fun occupySeat(seatDTO: SeatDTO): Response<SeatDTO> {
        return Response.of(false, "좌석 정보 API서버가 응답하지 않습니다.", null)
    }

}