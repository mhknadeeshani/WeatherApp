package com.vanguard.weather.service;

import com.vanguard.weather.exception.InvalidInputException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class InputValidationServiceTest {

    private InputValidationService inputValidationService;

    @BeforeEach
    void setUp() {
        inputValidationService = new InputValidationService();
    }

    @Test
    void validateInputWithValidInput() {
        String countryName = "Sri Lanka";
        String cityName = "Colombo";
        inputValidationService.validateInput(countryName, cityName);
    }

    @Test
    void validateInputWithInvalidInput() {
        String countryName = "Sri Lanka";
        String cityName = "";
        Assertions.assertThrows(InvalidInputException.class, () -> inputValidationService.validateInput(countryName, cityName));
    }
}