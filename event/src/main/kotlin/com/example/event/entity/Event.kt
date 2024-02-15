package com.example.event.entity

import com.example.event.type.Status
import jakarta.persistence.*

@Entity
class Event(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long?,

    @Column(unique = false)
    val ownerId : Long,

    @Column
    val name : String,

    @Column
    @Enumerated(value = EnumType.STRING)
    val status : Status,

) {
    @OneToMany(mappedBy = "event", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    val seats : MutableList<Seat> = mutableListOf()
}