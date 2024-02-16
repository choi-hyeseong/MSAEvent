package com.example.reservation.component.client.user

import com.example.reservation.component.client.AbstractFallbackFactory
import com.example.response.Response
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component

@Component
class UserFallbackFactory(objectMapper: ObjectMapper) : AbstractFallbackFactory<UserFallback>(objectMapper) {
    override fun provideFallback(): UserFallback {
       return UserFallback()
    }
}

class UserFallback : UserAPIClient {
    override fun check(id: Long): Response<Nothing> {
        return Response.of(false, "유저 API가 응답하지 않습니다.", null)
    }

}