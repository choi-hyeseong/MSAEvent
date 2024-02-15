package com.example.event.component.kafka.consumer

import com.example.event.dto.SeatDTO
import com.example.event.dto.SeatSaveDTO
import com.example.event.service.EventService
import com.example.response.logger
import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class KafkaConsumer (
    private val eventService: EventService,
    private val objectMapper: ObjectMapper,
){

    private val log = logger()

    @Transactional
    @KafkaListener(topics = ["occupy"], groupId = "occupy_group_1")
    fun read(data : String) {
        log.info("Kafka Consumed Data. topic : occupy")
        kotlin.runCatching {
            val saveRequest = objectMapper.readValue(data, SeatSaveDTO::class.java)
            CoroutineScope(Dispatchers.IO).launch {
                eventService.saveSeat(saveRequest)
            }
        }.onSuccess {
            log.info("좌석 정보 저장에 성공하였습니다.")
        }.onFailure {
            log.warn("좌석 정보 저장중 오류가 발생했습니다.")
        }
    }
}