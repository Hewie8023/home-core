package com.hewie.home.controller;

import com.hewie.home.entity.ResponseResult;
import com.hewie.home.entity.WeatherResponse;
import com.hewie.home.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/weather")
    public WeatherResponse getWeather() {
        //todo 后续如果需要定制地点，需要新增地图服务
        return weatherService.getCurrentWeather("xiaoshan");
    }
}
