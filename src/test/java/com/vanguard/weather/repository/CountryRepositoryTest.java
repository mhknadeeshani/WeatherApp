package com.vanguard.weather.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CountryRepositoryTest {

    private CountryRepository countryRepository;

    @BeforeEach
    void setUp() {
        countryRepository = new CountryRepository();
    }

    @Test
    void findCountryCodeByName() {
        String countryName = "Sri Lanka";
        String countryCode = countryRepository.findCountryCodeByName(countryName);
        Assertions.assertNotNull(countryCode);
        Assertions.assertEquals(countryCode, "LK");
    }
}