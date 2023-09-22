package com.vanguard.weather.service;

import com.vanguard.weather.repository.CountryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

class CountryServiceTest {

    private CountryService countryService;

    @Spy
    private CountryRepository countryRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        countryService = new CountryService(countryRepository);
    }

    @Test
    void getCountryCodeByName() {
        String countryName = "Sri Lanka";
        String countryCode = countryService.getCountryCodeByName(countryName);
        Assertions.assertNotNull(countryCode);
        Assertions.assertEquals(countryCode, "LK");
    }
}