package com.hewie.home.entity;

import lombok.Data;

@Data
public class Main {
    private Double temp;
    private Double feelsLike;
    private Double tempMin;
    private Double tempMax;
    private Double pressure;
    private Double humidity;
    private Double seaLevel;
    private Double grndLevel;
}
