package com.vanguard.weather.service;

import com.vanguard.weather.exception.InvalidInputException;
import com.vanguard.weather.util.Constants;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Slf4j
@Service
@AllArgsConstructor
public class InputValidationService {

    public void validateInput(String countryName, String cityName) {
        if (!StringUtils.hasText(countryName) || !StringUtils.hasText(cityName)) {
            log.error("Invalid input received - countryName-{}, cityName-{}", countryName, cityName);
            throw new InvalidInputException(Constants.INVALID_INPUT_ERROR);
        }
    }

}
