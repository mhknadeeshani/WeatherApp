package com.vanguard.weather.service;

import com.vanguard.weather.exception.InvalidAPIKeyException;
import com.vanguard.weather.repository.APIKeyRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class APIKeyValidationServiceTest {

    private APIKeyValidationService apiKeyValidationService;

    @Spy
    private APIKeyRepository apiKeyRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        apiKeyValidationService = new APIKeyValidationService(apiKeyRepository);
    }

    @Test
    void validateAPIKeyWithValidKey() {
        String apiKey = "821ea665e94bc5a0ee2456c41de5f3ea";
        apiKeyValidationService.validateAPIKey(apiKey);
        verify(apiKeyRepository, times(1)).isValidAPIKey(apiKey);
    }

    @Test
    void validateAPIKeyWithInvalidKey() {
        String apiKey = "invalid_key";
        Assertions.assertThrows(InvalidAPIKeyException.class, () -> apiKeyValidationService.validateAPIKey(apiKey));
        verify(apiKeyRepository, times(1)).isValidAPIKey(apiKey);
    }
}