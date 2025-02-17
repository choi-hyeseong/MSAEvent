package com.example.event.aop.aspect

import com.example.event.aop.anno.RedissonLock
import com.example.event.dto.SeatDTO
import com.example.event.exception.EventException
import com.example.response.logger
import kotlinx.coroutines.*
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.redisson.api.RLock
import org.redisson.api.RedissonClient
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.util.concurrent.TimeUnit

@Aspect
@Component
class RedisLockAspect(private val client : RedissonClient) {

    private val logger = logger()

    @Around("@annotation(com.example.event.aop.anno.RedissonLock)")
    fun redissonLock(joinPoint: ProceedingJoinPoint) : Any {
        val signature : MethodSignature = joinPoint.signature as MethodSignature
        val method = signature.method

        // annotation info
        val annotation = method.getAnnotation(RedissonLock::class.java)
        val paramDTO = joinPoint.args[0] as SeatDTO
        // lock key
        val key = "occupy-${method.name}-${paramDTO.eventId}-${paramDTO.seatId}"
        // lock
        val lock : RLock = client.getLock(key)
        try {
            val status = lock.tryLock(annotation.waitTime, annotation.leaseTime, TimeUnit.MILLISECONDS)
            if (!status) {
                logger.warn("cannot retrieve lock")
                throw EventException("lock을 가져올 수 없습니다.", null)
            }
            logger.info("get lock")
            val result : Any = runBlocking {
                withContext(Dispatchers.IO) {
                    joinPoint.proceed(joinPoint.args)
                }
            }
            return result
        }
        catch (e : InterruptedException) {
            logger.error("retreive interrupted exception")
            throw e
        }
        finally {
            // 락이 되어 있는경우만 언락
            if (lock.isLocked && lock.isHeldByCurrentThread)
                lock.unlock()
        }
    }
}