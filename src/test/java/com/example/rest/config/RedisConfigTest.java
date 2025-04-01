package com.example.rest.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class RedisConfigTest {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void testRedisConnection() {
        // Test basic set and get operations
        ValueOperations<String, Object> valueOps = redisTemplate.opsForValue();
        
        // Set a value
        valueOps.set("test:key", "Hello Redis!");
        
        // Get the value
        String value = (String) valueOps.get("test:key");
        assertEquals("Hello Redis!", value);
        
        // Delete the value
        redisTemplate.delete("test:key");
        
        // Verify deletion
        assertNull(valueOps.get("test:key"));
    }
} 