package com.example.event.component.redis

import org.redisson.Redisson
import org.redisson.api.RedissonClient
import org.redisson.config.Config
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RedisConfig(@Value("\${redis-ip}") private val host: String) {

    companion object {
        private const val REDIS_PREFIX = "redis://"
    }

    // redis setup
    @Bean
    fun redisClient() : RedissonClient {
        val config = Config().apply {
            useSingleServer().setAddress(REDIS_PREFIX + host)
        }
        return Redisson.create(config)
    }
}