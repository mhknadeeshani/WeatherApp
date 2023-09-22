package com.vanguard.weather.repository;

import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Repository
public class CountryRepository {

    private static final Map<String, String> countryMap = new HashMap<>();


    static {
        Arrays.asList(Locale.getISOCountries()).stream()
                .map(countryCode -> new Locale("", countryCode))
                .forEach(country -> countryMap.put(country.getDisplayCountry(), country.getCountry()));
    }

    public String findCountryCodeByName(String countryName) {
        return countryMap.getOrDefault(countryName, "");
    }
}
