package com.vanguard.weather.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class APIKeyRepositoryTest {

    private APIKeyRepository apiKeyRepository;

    @BeforeEach
    void setUp() {
        apiKeyRepository = new APIKeyRepository();
    }

    @Test
    void isValidAPIKeyWithValidKey() {
        String apiKey = "821ea665e94bc5a0ee2456c41de5f3ea";
        boolean isValidKey = apiKeyRepository.isValidAPIKey(apiKey);
        Assertions.assertTrue(isValidKey);
    }

    @Test
    void isValidAPIKeyWithInvalidKey() {
        String apiKey = "invalid_key";
        boolean isValidKey = apiKeyRepository.isValidAPIKey(apiKey);
        Assertions.assertFalse(isValidKey);
    }
}