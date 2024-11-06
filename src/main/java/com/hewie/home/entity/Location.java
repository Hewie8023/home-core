package com.hewie.home.entity;

import lombok.Data;

@Data
public class Location {

    private String province;
    private String city;
    private String area;
    private String address;
    private String lng;
    private String lat;
    private String ip;

}
