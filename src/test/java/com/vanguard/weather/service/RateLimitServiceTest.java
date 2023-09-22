package com.vanguard.weather.service;

import io.github.bucket4j.Bucket;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class RateLimitServiceTest {

    private RateLimitService rateLimitService;

    @BeforeEach
    void setUp() {
        rateLimitService = new RateLimitService();
        ReflectionTestUtils.setField(rateLimitService, "duration", 60);
    }

    @Test
    void resolveBucket() {
        String apiKey = "dummyApiKey";
        Bucket bucket = rateLimitService.resolveBucket(apiKey);
        Assertions.assertNotNull(bucket);
        Assertions.assertEquals(bucket.getAvailableTokens(), 5);
    }
}