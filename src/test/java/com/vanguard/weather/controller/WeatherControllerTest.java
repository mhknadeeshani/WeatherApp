package com.vanguard.weather.controller;

import com.vanguard.weather.model.WeatherRecord;
import com.vanguard.weather.service.*;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WeatherControllerTest {

    @Mock
    private RateLimitService rateLimitService;
    @Mock
    private WeatherService weatherService;
    @Mock
    private CountryService countryService;
    @Mock
    private UtilityService utilityService;
    @Mock
    private APIKeyValidationService apiKeyValidationService;
    @Mock
    private InputValidationService inputValidationService;

    @InjectMocks
    private WeatherController weatherController;

    @Test
    void getWeatherDescription() {
        String countryName = "Sri Lanka";
        String cityName = "Colombo";
        String apiKey = "f7e36d8ed0a99c3a7e16c991e981e042";
        String pattern = "MM-dd-yyyy:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());
        when(rateLimitService.resolveBucket(apiKey)).thenReturn(Bucket.builder().addLimit(Bandwidth.simple(5, Duration.ofHours(1))).build());
        when(countryService.getCountryCodeByName(countryName)).thenReturn("LK");
        when(utilityService.currentDate()).thenReturn(date);
        when(weatherService.findWeatherDetails("LK", cityName, date))
                .thenReturn(WeatherRecord.builder()
                        .Id(10001L)
                        .description("testDescription")
                        .countryCode("LK")
                        .city(cityName)
                        .requestedTime(date)
                        .build());
        ResponseEntity<String> weatherResponse = weatherController.getWeatherDescription(countryName, cityName, apiKey);
        verify(apiKeyValidationService, times(1)).validateAPIKey(apiKey);
        verify(inputValidationService, times(1)).validateInput(countryName, cityName);
        Assertions.assertNotNull(weatherResponse);
        Assertions.assertEquals(weatherResponse.getStatusCode(), HttpStatusCode.valueOf(200));
        Assertions.assertEquals(weatherResponse.getBody(), "testDescription");

    }

}