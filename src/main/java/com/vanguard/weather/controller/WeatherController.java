package com.vanguard.weather.controller;

import com.vanguard.weather.model.Weather;
import com.vanguard.weather.service.RateLimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@RestController
public class WeatherController {


    private RateLimitService rateLimitService;

    @GetMapping(value = "/weather")
    public ResponseEntity<Weather> weatherDetails(@RequestParam(value = "country") String countryName,
                                                        @RequestParam(value = "city") String cityName,
                                                        @RequestHeader(value = "appid") String apiKey) {

        String uri = "http://api.openweathermap.org/geo/1.0/direct?q="+cityName+","+countryName+"&limit=5&appid="+apiKey;
        RestTemplate template = new RestTemplate();
        ResponseEntity result =  template.getForEntity(uri,Weather.class );
        return result;


    }

    @GetMapping(value = "/health")
    public String healthCheck(){
        return "Success";
    }


    @GetMapping(value = "/test")
    public String testCheck(@RequestParam(value = "country") String countryName, @RequestParam(value = "city") String cityName,
                            @RequestHeader(value = "appid") String apiKey){
        return "Success "+countryName+" "+cityName +" "+apiKey;
    }

}
