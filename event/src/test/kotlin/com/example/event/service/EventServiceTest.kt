package com.example.event.service

import com.example.event.dto.EventRequestDTO
import com.example.event.dto.SeatRequestDTO
import com.example.event.exception.EventException
import com.example.event.type.Status
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
class EventServiceTest {

    @Autowired
    private lateinit var eventService : EventService

    private final val seatRequestList = mutableListOf(SeatRequestDTO("A1", Status.OPEN), SeatRequestDTO("A2", Status.CLOSE))
    val eventRequestDTO = EventRequestDTO("테스트1", 0L, Status.OPEN, seatRequestList)

    fun CREATE_EVENT() : Long {
        val result = assertDoesNotThrow {
            runBlocking {
                eventService.createEvent(eventRequestDTO)
            }
        }
        return result.id
    }

    @Test
    @Transactional
    fun TEST_CREATE_EVENT() {
        val id = CREATE_EVENT()
        assertNotNull(id)
    }

    @Test
    @Transactional
    fun TEST_LOAD_EVENT() {
        val id = CREATE_EVENT()
        val loadEvent = assertDoesNotThrow { runBlocking {  eventService.findEvent(id) } }
        assertEquals(eventRequestDTO.name, loadEvent.name)
        assertEquals(seatRequestList.first().name, loadEvent.seats.first().name)
    }


    @Test
    @Transactional
    fun TEST_DOES_NOT_FIND_EVENT() {
        assertThrows(EventException::class.java) { runBlocking { eventService.findEvent(-1) } }
    }
}