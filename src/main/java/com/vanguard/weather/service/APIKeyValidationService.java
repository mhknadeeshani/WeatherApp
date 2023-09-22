package com.vanguard.weather.service;

import com.vanguard.weather.exception.InvalidAPIKeyException;
import com.vanguard.weather.repository.APIKeyRepository;
import com.vanguard.weather.util.Constants;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class APIKeyValidationService {

    private APIKeyRepository apiKeyRepository;

    public void validateAPIKey(String apiKey) {
        if (!apiKeyRepository.isValidAPIKey(apiKey)) {
            log.error("Invalid APIKey received");
            throw new InvalidAPIKeyException(Constants.INVALID_API_KEY_ERROR);
        }
    }
}
