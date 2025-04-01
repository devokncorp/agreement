package com.example.rest.config;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private int redisPort;

    @Value("${spring.redis.password}")
    private String redisPassword;

    @Value("${spring.redis.database}")
    private int redisDatabase;

    @Value("${spring.redis.lettuce.pool.max-active}")
    private int maxActive;

    @Value("${spring.redis.lettuce.pool.max-idle}")
    private int maxIdle;

    @Value("${spring.redis.lettuce.pool.min-idle}")
    private int minIdle;

    @Value("${spring.redis.lettuce.pool.max-wait}")
    private Duration maxWait;

    @Value("${spring.redis.sentinel.master:}")
    private String sentinelMaster;

    @Value("${spring.redis.sentinel.nodes:}")
    private String sentinelNodes;

    @Value("${spring.redis.sentinel.password:}")
    private String sentinelPassword;

    @Bean
    @Primary
    public RedisConnectionFactory redisConnectionFactory() {
        if (isSentinelEnabled()) {
            return createSentinelConnectionFactory();
        }
        return createStandaloneConnectionFactory();
    }

    private boolean isSentinelEnabled() {
        return sentinelMaster != null && !sentinelMaster.isEmpty() &&
               sentinelNodes != null && !sentinelNodes.isEmpty();
    }

    private RedisConnectionFactory createSentinelConnectionFactory() {
        RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration();
        sentinelConfig.master(sentinelMaster);

        // Configure sentinel nodes
        Set<String> sentinelHosts = new HashSet<>();
        for (String node : sentinelNodes.split(",")) {
            String[] hostPort = node.split(":");
            sentinelConfig.sentinel(hostPort[0], Integer.parseInt(hostPort[1]));
        }

        // Set password if configured
        if (sentinelPassword != null && !sentinelPassword.isEmpty()) {
            sentinelConfig.setPassword(sentinelPassword);
        }

        // Configure connection pool
        GenericObjectPoolConfig<?> poolConfig = createPoolConfig();
        LettucePoolingClientConfiguration clientConfig = LettucePoolingClientConfiguration.builder()
                .commandTimeout(Duration.ofSeconds(5))
                .poolConfig(poolConfig)
                .build();

        return new LettuceConnectionFactory(sentinelConfig, clientConfig);
    }

    private RedisConnectionFactory createStandaloneConnectionFactory() {
        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration(redisHost, redisPort);
        redisConfig.setPassword(redisPassword);
        redisConfig.setDatabase(redisDatabase);

        GenericObjectPoolConfig<?> poolConfig = createPoolConfig();
        LettucePoolingClientConfiguration clientConfig = LettucePoolingClientConfiguration.builder()
                .commandTimeout(Duration.ofSeconds(5))
                .poolConfig(poolConfig)
                .build();

        return new LettuceConnectionFactory(redisConfig, clientConfig);
    }

    private GenericObjectPoolConfig<?> createPoolConfig() {
        GenericObjectPoolConfig<?> poolConfig = new GenericObjectPoolConfig<>();
        poolConfig.setMaxTotal(maxActive);
        poolConfig.setMaxIdle(maxIdle);
        poolConfig.setMinIdle(minIdle);
        poolConfig.setMaxWaitMillis(maxWait.toMillis());
        return poolConfig;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        
        // Use StringRedisSerializer for keys
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        
        // Use GenericJackson2JsonRedisSerializer for values
        GenericJackson2JsonRedisSerializer jsonSerializer = new GenericJackson2JsonRedisSerializer();
        template.setValueSerializer(jsonSerializer);
        template.setHashValueSerializer(jsonSerializer);
        
        template.afterPropertiesSet();
        return template;
    }
} 