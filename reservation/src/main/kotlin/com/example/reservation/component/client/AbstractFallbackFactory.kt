package com.example.reservation.component.client

import com.example.reservation.component.client.event.EventFallback
import com.example.reservation.exception.ReservationException
import com.example.response.Response
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import feign.FeignException
import org.springframework.cloud.openfeign.FallbackFactory
import kotlin.jvm.optionals.getOrNull

abstract class AbstractFallbackFactory<T>(private val objectMapper: ObjectMapper) : FallbackFactory<T> {
    override fun create(cause: Throwable?): T {
        val jsonExceptionMessage = deserialize(cause)
        if (jsonExceptionMessage != null)
            throw ReservationException(jsonExceptionMessage, null)
        return provideFallback()
    }

    abstract fun provideFallback() : T

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