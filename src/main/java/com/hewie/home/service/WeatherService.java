package com.hewie.home.service;

import com.hewie.home.entity.*;
import com.hewie.home.utils.WeatherConvert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class WeatherService {

    @Value("${openweathermap.appid}")
    private String appId;

    private final RestTemplate restTemplate;

    public WeatherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public WeatherResponse getCurrentWeather(String city) {
        String url = String.format("http://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s", city, appId);
        WeatherEntity weather = restTemplate.getForObject(url, WeatherEntity.class);
        WeatherRes weatherRes = new WeatherRes();
        weatherRes.setWeather(WeatherConvert.convert(weather));
        log.info("weather === >" + weatherRes.getWeather().getWeather() + "===>" + weatherRes.getWeather().getTemp());
        return WeatherResponse.SUCCESS().setData(weatherRes);
    }
}
