package com.vanguard.weather.service;

import com.vanguard.weather.model.WeatherRecord;
import com.vanguard.weather.repository.WeatherRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WeatherServiceTest {

    @Spy
    private WeatherRepository weatherRepository;
    @Mock
    private LocationService locationService;
    @Mock
    private UtilityService utilityService;
    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private WeatherService weatherService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(weatherService, "weatherURL", "dummyURL");
    }

    @Test
    void saveWeather() {
        WeatherRecord weatherRecord = WeatherRecord.builder().build();
        weatherService.saveWeather(weatherRecord);
        verify(weatherRepository, times(1)).save(weatherRecord);
    }

    @Test
    void findWeatherDetails() {
        String country = "LK";
        String city = "Colombo";
        String requestedTime = "2023:09:26:13";
        when(weatherRepository.findByCountryCodeAndCityAndRequestedTime(country, city, requestedTime))
                .thenReturn(WeatherRecord.builder().Id(10001L).requestedTime(requestedTime).city(city).countryCode(country).description("testDescription")
                        .build());
        WeatherRecord weatherDetails = weatherService.findWeatherDetails(country, city, requestedTime);
        verify(weatherRepository, times(1)).findByCountryCodeAndCityAndRequestedTime(any(), any(), any());
        Assertions.assertNotNull(weatherDetails);
        Assertions.assertEquals(weatherDetails.getDescription(), "testDescription");
        Assertions.assertEquals(weatherDetails.getCountryCode(), country);
        Assertions.assertEquals(weatherDetails.getCity(), city);
    }

    @Test
    void callWeatherApi() {
    }

    @Test
    void getWeatherData() {
    }
}