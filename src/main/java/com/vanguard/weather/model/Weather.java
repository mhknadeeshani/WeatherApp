package com.vanguard.weather.model;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Weather {

 private String name;
 private String lat;
 private String lon;
 private String country;
 private String state;
}
