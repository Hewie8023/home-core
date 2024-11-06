package com.hewie.home.entity;

import lombok.Data;

import java.util.Date;

@Data
public class WeatherResponse {
    private int code;
    private int error;
    private Object data;
    private String time;

    public static WeatherResponse SUCCESS() {
        WeatherResponse weatherResponse = new WeatherResponse();
        weatherResponse.code = 0;
        weatherResponse.error = 0;
        weatherResponse.time = new Date().toString();
        return weatherResponse;
    }

    public WeatherResponse setData(Object data) {
        this.data = data;
        return this;
    }
}
