package com.example.reservation.component.client.config

import feign.Retryer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FeignConfig {

    @Bean
    fun provideRetryer() : Retryer {
        /**
         * 1초마다 1번씩 총 5번 재시도하는 Retryer
         */
        return Retryer.Default()
    }
}