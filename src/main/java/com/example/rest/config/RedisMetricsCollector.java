package com.example.rest.config;

import io.lettuce.core.api.StatefulRedisConnection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RedisMetricsCollector {

    private final LettuceConnectionFactory connectionFactory;

    @Autowired
    public RedisMetricsCollector(LettuceConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Scheduled(fixedRate = 60000) // Run every minute
    public void collectMetrics() {
        try {
            log.info("Redis Connection Pool Metrics:");
            log.info("Active: {}", connectionFactory.getValidateConnection());
            log.info("Shared Native Connections: {}", connectionFactory.getShareNativeConnection());
            log.info("Connection Timeout: {}ms", connectionFactory.getTimeout());
            
        } catch (Exception e) {
            log.error("Error collecting Redis metrics: {}", e.getMessage(), e);
        }
    }
} 