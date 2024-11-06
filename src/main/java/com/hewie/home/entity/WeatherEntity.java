package com.hewie.home.entity;

import lombok.Data;

import java.util.List;

@Data
public class WeatherEntity {
    private Coord coord;
    private List<Weather> weather;
    private Main main;
    private String base;
    private int visibility;
    private Long dt;
    private int timezone;
    private int cod;
    private int id;
    private String name;
    private Sys sys;
    private Wind wind;
    private Clouds clouds;
}
