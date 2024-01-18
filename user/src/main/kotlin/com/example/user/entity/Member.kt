package com.example.user.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class Member (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id : Long?,

    @Column(name = "username", unique = true)
    var username : String,

    var password : String,

    @Column(name = "nickname", unique = true)
    var nickname : String

)