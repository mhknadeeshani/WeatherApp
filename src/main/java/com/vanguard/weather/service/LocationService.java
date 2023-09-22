package com.vanguard.weather.service;

import com.vanguard.weather.exception.InvalidLocationException;
import com.vanguard.weather.model.Location;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocationService {

    @Value("${weather.api.location.url}")
    private String locationURL;

    @Value("${weather.api.location.params}")
    private String locationParams;
    private final CountryService countryService;

    private final RestTemplate restTemplate;

    public Location getLocation(String countryName, String cityName, String apiKey) {
        String countryCode = countryService.getCountryCodeByName(countryName);
        String uri = String.format("%s%s,%s%s%s", locationURL, cityName, countryCode, locationParams, apiKey);
        ResponseEntity<Location[]> responseEntity = restTemplate.getForEntity(uri, Location[].class);
        Location[] locations = responseEntity.getBody();

        if (locations == null || locations.length == 0) {
            log.error("Invalid response received from weather api for location");
            throw new InvalidLocationException("Invalid location received. Please verify the input");
        }

        Location location = locations[0];
        location.setCountryName(countryName);
        return location;
    }
}
