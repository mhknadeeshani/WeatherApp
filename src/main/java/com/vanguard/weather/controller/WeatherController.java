package com.vanguard.weather.controller;

import com.vanguard.weather.model.WeatherRecord;
import com.vanguard.weather.service.*;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
public class WeatherController {

    private RateLimitService rateLimitService;
    private WeatherService weatherService;
    private CountryService countryService;
    private UtilityService utilityService;
    private APIKeyValidationService apiKeyValidationService;
    private InputValidationService inputValidationService;

    @GetMapping(value = "/weather")
    public ResponseEntity<String> getWeatherDescription(@RequestParam(value = "countryName") String countryName,
                                                        @RequestParam(value = "city") String cityName,
                                                        @RequestHeader(value = "apiKey") String apiKey) {
        inputValidationService.validateInput(countryName, cityName);
        apiKeyValidationService.validateAPIKey(apiKey);
        Bucket bucket = rateLimitService.resolveBucket(apiKey);
        ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);
        WeatherRecord currentWeather = null;
        if (probe.isConsumed()) {
            String countryCode = countryService.getCountryCodeByName(countryName);
            String date = utilityService.currentDate();
            WeatherRecord existingWeatherRecord = weatherService.findWeatherDetails(countryCode, cityName, date);
            if (existingWeatherRecord != null) {
                log.info("Retrieve data from database");
                currentWeather = existingWeatherRecord;
            } else {
                log.info("Retrieve data from open weather map api");
                currentWeather = weatherService.callWeatherApis(countryName, cityName, apiKey);
            }

            return ResponseEntity.ok()
                    .header("Rate-Limit-Remaining", Long.toString(probe.getRemainingTokens()))
                    .body(currentWeather.getDescription());
        }

        long waitForRefill = probe.getNanosToWaitForRefill() / 1000000000;
        long waitInMins = waitForRefill / 60;
        log.info("Maximum no of requests reached");
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                .header("Rate-Limit-Retry-After-Seconds", String.valueOf(waitInMins))
                .body("Maximum number of requests reached. Retry after " + String.valueOf(waitInMins) + " minutes.");

    }

    @GetMapping(value = "/health")
    public String healthCheck() {
        return "Success";
    }


}
