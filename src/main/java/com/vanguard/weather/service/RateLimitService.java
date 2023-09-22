package com.vanguard.weather.service;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.vanguard.weather.util.Constants.API_RATE_LIMIT;

@Slf4j
@Service
@RequiredArgsConstructor
public class RateLimitService {

    @Value("${weather.request.duration.min:60}")
    private int duration;

    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();

    public Bucket resolveBucket(String apiKey) {
        return cache.computeIfAbsent(apiKey, this::newBucket);
    }

    private Bucket newBucket(String apiKey) {
        Bucket bucket = null;
        try {
            Bandwidth limit = Bandwidth.classic(API_RATE_LIMIT, Refill.intervally(API_RATE_LIMIT, Duration.ofMinutes(duration)));
            bucket = Bucket.builder()
                    .addLimit(limit)
                    .build();
        } catch (Exception e) {
            log.error("Exception occurred while creating bucket", e);
        }
        return bucket;
    }
}
