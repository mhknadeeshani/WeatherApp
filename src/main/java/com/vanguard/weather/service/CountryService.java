package com.vanguard.weather.service;

import com.vanguard.weather.exception.InvalidInputException;
import com.vanguard.weather.repository.CountryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Slf4j
@Service
@AllArgsConstructor
public class CountryService {

    private CountryRepository countryRepository;

    public String getCountryCodeByName(String countryName) {

        String countryCode = countryRepository.findCountryCodeByName(countryName);
        if (!StringUtils.hasText(countryCode)) {
            log.error("Invalid countryName received. countryName - {}", countryName);
            throw new InvalidInputException("Invalid country name. Please verify the input");
        }
        return countryCode;
    }
}
