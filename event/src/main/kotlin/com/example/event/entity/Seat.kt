package com.example.event.entity

import com.example.event.type.Status
import jakarta.persistence.*

@Entity
class Seat (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long?,

    @Column(length = 50, unique = false)
    val name : String,

    @Enumerated(value = EnumType.STRING)
    @Column
    var status : Status,

    @JoinColumn(name = "event_id")
    @ManyToOne
    val event : Event

)