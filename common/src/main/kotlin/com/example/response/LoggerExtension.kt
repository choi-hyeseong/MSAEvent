package com.example.response

import org.slf4j.LoggerFactory

class LoggerExtension

inline fun <reified T> T.logger() = LoggerFactory.getLogger(T::class.java)