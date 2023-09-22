package com.vanguard.weather.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class WeatherKey implements Serializable {

    private Long Id;

    private String countryCode;

    private String city;

    private String requestedTime;
}
