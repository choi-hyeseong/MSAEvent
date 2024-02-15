package com.example.reservation.exception

class ReservationException(message : String, val id : Long?) : Exception(message)