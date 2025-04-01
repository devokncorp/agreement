package com.example.rest.config;

import io.lettuce.core.resource.ClientResources;
import io.lettuce.core.resource.DefaultClientResources;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

@Configuration
public class RedisMetricsConfig {

    @Bean(destroyMethod = "shutdown")
    public ClientResources clientResources(MeterRegistry meterRegistry) {
        return DefaultClientResources.builder()
                .build();
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory(ClientResources clientResources) {
        LettuceConnectionFactory factory = new LettuceConnectionFactory();
        factory.setClientResources(clientResources);
        return factory;
    }
} 