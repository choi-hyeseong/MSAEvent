package com.example.gateway.filter

import com.example.gateway.logger
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

//약간 Spring Servlet Filter 느낌인듯
@Component
class GlobalFilter : AbstractGatewayFilterFactory<Config>(Config::class.java) {

    val log = logger()

    override fun apply(config: Config?): GatewayFilter {
        return GatewayFilter { exchange, chain ->
            val request = exchange.request
            val response = exchange.response
            log.info("Global Filter Accepts request. id : {} uri : {}", request.id, request.uri)

            chain.filter(exchange).then(Mono.fromRunnable {
                log.info("Global Filter Respose code : {}", response.statusCode)
            })
        }
    }
}

//Config는 properties의 arg에 매핑
class Config