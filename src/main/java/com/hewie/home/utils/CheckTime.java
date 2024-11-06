package com.hewie.home.utils;

import java.time.LocalTime;

public class CheckTime {

    public static Boolean getTime() {
        LocalTime currentTime = LocalTime.now();
        return currentTime.isAfter(LocalTime.of(3, 0)) && currentTime.isBefore(LocalTime.of(19, 0));
    }

}
