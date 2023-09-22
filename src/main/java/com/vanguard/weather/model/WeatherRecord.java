package com.vanguard.weather.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(WeatherKey.class)
@Table(name = "Weather")
public class WeatherRecord {

    @Id
    @GeneratedValue
    private Long Id;

    @Id
    private String countryCode;

    private String countryName;

    @Id
    private String city;

    @Id
    private String requestedTime;

    private String state;

    private String description;


}
