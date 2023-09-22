package com.vanguard.weather.repository;

import com.vanguard.weather.model.WeatherRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherRepository extends JpaRepository<WeatherRecord, Long> {

    WeatherRecord findByCountryCodeAndCityAndRequestedTime(String country, String city, String requestDateTime);
}
