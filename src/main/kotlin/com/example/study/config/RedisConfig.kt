package com.example.study.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class RedisConfig {

    @Value("\${redis.host}")
    private lateinit var redisHost: String

    @Value("\${redis.port}")
    private lateinit var redisPort : Number
}