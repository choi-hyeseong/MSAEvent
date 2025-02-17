package com.example.event.aop.anno

/**
 * 해당 메소드에 락을 거는 메소드 using redis
 * @property value 락을 사용할 이름
 * @property waitTime 락 점유를 기다리는 시간 - 기본값 5s
 * @property leaseTime 락을 소유하는 시간 - 기본값 2초
 */
annotation class RedissonLock(
    val value : String = "",
    val waitTime : Long = 5000L,
    val leaseTime : Long = 2000L
)
