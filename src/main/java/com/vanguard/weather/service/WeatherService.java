package com.vanguard.weather.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vanguard.weather.model.Location;
import com.vanguard.weather.model.ResponseDTO;
import com.vanguard.weather.model.Weather;
import com.vanguard.weather.model.WeatherRecord;
import com.vanguard.weather.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherService {

    @Value("${weather.api.weather.url}")
    private String weatherURL;

    private final WeatherRepository weatherRepository;

    private final LocationService locationService;

    private final UtilityService utilityService;

    private final RestTemplate restTemplate;

    public void saveWeather(WeatherRecord weatherRecord) {
        weatherRepository.save(weatherRecord);
    }

    public WeatherRecord findWeatherDetails(String country, String city, String dateTime) {
        return weatherRepository.findByCountryCodeAndCityAndRequestedTime(country, city, dateTime);
    }

    public WeatherRecord callWeatherApis(String countryName, String cityName, String apiKey) {
        WeatherRecord weatherRecord = null;
        Location location = locationService.getLocation(countryName, cityName, apiKey);
        ResponseDTO responseDTO = getWeatherData(location, apiKey);
        weatherRecord = buildWeatherRecord(location, responseDTO);
        saveWeather(weatherRecord);
        return weatherRecord;
    }

    public ResponseDTO getWeatherData(Location location, String apiKey) {
        String uri = String.format("%s%s&lon=%s&appid=%s", weatherURL, location.getLat(), location.getLon(), apiKey);
        ResponseEntity<Object> responseEntity2 = restTemplate.getForEntity(uri, Object.class);
        Object object = responseEntity2.getBody();
        ObjectMapper mapper = new ObjectMapper();
        ResponseDTO responseDTO = mapper.convertValue(object, ResponseDTO.class);
        return responseDTO;
    }

    private WeatherRecord buildWeatherRecord(Location location, ResponseDTO responseDTO) {
        return WeatherRecord.builder()
                .city(location.getName())
                .countryCode(location.getCountryCode())
                .countryName(location.getCountryName())
                .state(location.getState())
                .requestedTime(utilityService.currentDate())
                .description(Optional.ofNullable(responseDTO.getWeather()).orElseGet(() -> Collections.emptyList()).stream()
                        .map(Weather::getDescription).collect(Collectors.joining(".")))
                .build();
    }
}
