package com.vanguard.weather.service;

import com.vanguard.weather.model.Location;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LocationServiceTest {

    @Mock
    private CountryService countryService;
    @Mock
    private RestTemplate restTemplate;

    private String locationURL;

    private String locationParams;

    private LocationService locationService;

    @BeforeEach
    void setUp() {
        locationURL = "http://api.openweathermap.org/geo/1.0/direct?q=";
        locationParams = "&limit=5&appid=";
        MockitoAnnotations.openMocks(this);
        locationService = new LocationService(countryService, restTemplate);
        ReflectionTestUtils.setField(locationService, "locationURL", locationURL);
        ReflectionTestUtils.setField(locationService, "locationParams", locationParams);
    }

    @Test
    void getLocation() {
        String countryName = "Sri Lanka";
        String cityName = "Colombo";
        String apiKey = "dummyAPIKey";
        String countryCode = "LK";
        String uri = String.format("%s%s,%s%s%s",locationURL, cityName, countryCode, locationParams, apiKey);
        Location[] locations = {Location.builder().countryCode("LK").countryName(countryName).build()};
        when(countryService.getCountryCodeByName(countryName)).thenReturn(countryCode);
        when(restTemplate.getForEntity(uri, Location[].class ))
                .thenReturn(new ResponseEntity(locations, HttpStatus.OK));
        Location location = locationService.getLocation(countryName, cityName, apiKey);
        Assertions.assertNotNull(location);
        Assertions.assertEquals(location.getCountryCode(), "LK");
        Assertions.assertEquals(location.getCountryName(), countryName);
    }
}