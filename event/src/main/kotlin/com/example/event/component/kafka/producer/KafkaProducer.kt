package com.example.event.component.kafka.producer

import com.example.event.dto.SeatSaveDTO
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

//실제 메시지를 보내는 producer
@Component
class KafkaProducer(val kafkaTemplate: KafkaTemplate<String, String>, val objectMapper: ObjectMapper) {

    private fun send(topic : String, data : String) {
        kafkaTemplate.send(topic, data)
    }

    fun sendOccupyRequest(seatSaveDTO: SeatSaveDTO) {
        send("occupy", objectMapper.writeValueAsString(seatSaveDTO))
    }
}