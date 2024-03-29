package com.example.event

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.openfeign.EnableFeignClients

@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
class EventApplication

fun main(args: Array<String>) {
    runApplication<EventApplication>(*args)
}
