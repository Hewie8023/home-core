package com.hewie.home.utils;

import com.hewie.home.entity.CurrentWeather;
import com.hewie.home.entity.WeatherEntity;

public class WeatherConvert {
    public static CurrentWeather convert(WeatherEntity entity) {
        if (entity == null) return new CurrentWeather();
        CurrentWeather weather = new CurrentWeather();
        weather.setCityname("杭州滨江");
        weather.setWeather(entity.getWeather().get(0).getMain());
        //todo 天气code转换
        switch (entity.getWeather().get(0).getId()) {
            case 800: {
                weather.setWeathercode(CheckTime.getTime() ? WeatherCode.icon1 : WeatherCode.icon2);
                break;
            }
            case 801:
            case 802:
            case 803: {
                weather.setWeathercode(CheckTime.getTime() ? WeatherCode.icon3 : WeatherCode.icon4);
                break;
            }
            case 804: {
                weather.setWeathercode(WeatherCode.icon5);
                break;
            }
            case 701:
            case 741: {
                weather.setWeathercode(WeatherCode.icon21);
                break;
            }
            case 711: {
                weather.setWeathercode(WeatherCode.icon35);
                break;
            }
            case 721: {
                weather.setWeathercode(WeatherCode.icon39);
                break;
            }
            case 731: {
                weather.setWeathercode(WeatherCode.icon23);
            }
            case 751: {
                weather.setWeathercode(WeatherCode.icon33);
                break;
            }
            case 761: {
                weather.setWeathercode(WeatherCode.icon32);
                break;
            }
            case 771: {
                weather.setWeathercode(WeatherCode.icon20);
                break;
            }
            case 600: {
                weather.setWeathercode(WeatherCode.icon17);
                break;
            }
            case 601: {
                weather.setWeathercode(WeatherCode.icon44);
                break;
            }
            case 602: {
                weather.setWeathercode(WeatherCode.icon19);
                break;
            }
            case 611:
            case 612:
            case 613:
            case 615:
            case 616:{
                weather.setWeathercode(WeatherCode.icon9);
                break;
            }
            case 620: {
                weather.setWeathercode(WeatherCode.icon29);
                break;            }
            case 621: {
                weather.setWeathercode(WeatherCode.icon16);
                break;
            }
            case 622: {
                weather.setWeathercode(WeatherCode.icon31);
                break;
            }
            case 500: {
                weather.setWeathercode(WeatherCode.icon10);
                break;
            }
            case 501: {
                weather.setWeathercode(WeatherCode.icon11);
                break;
            }
            case 502: {
                weather.setWeathercode(WeatherCode.icon12);
                break;
            }
            case 503: {
                weather.setWeathercode(WeatherCode.icon13);
                break;
            }
            case 504: {
                weather.setWeathercode(WeatherCode.icon14);
                break;
            }
            case 511: {
                weather.setWeathercode(WeatherCode.icon22);
                break;
            }
            case 520: {
                weather.setWeathercode(WeatherCode.icon25);
                break;
            }
            case 521: {
                weather.setWeathercode(WeatherCode.icon6);
                break;
            }
            case 522: {
                weather.setWeathercode(WeatherCode.icon26);
                break;
            }
            case 531: {
                weather.setWeathercode(WeatherCode.icon15);
                break;
            }
            case 300:
            case 301:
            case 302:
            case 310:
            case 311:{
                weather.setWeathercode(WeatherCode.icon43);
                break;
            }
            case 312:
            case 313:
            case 314:
            case 321: {
                weather.setWeathercode(WeatherCode.icon6);
                break;
            }
            case 200:
            case 201:
            case 202:
            case 210:
            case 211:
            case 212:
            case 221:
            case 230:
            case 231:
            case 232: {
                weather.setWeathercode(WeatherCode.icon7);
                break;
            }
            default: {
                weather.setWeathercode(WeatherCode.icon99);
            }
        }


        weather.setTemp(String.format("%.0f", TemperatureConverter.convertKelvinToCelsius(entity.getMain().getTemp())));

        return weather;
    }
}
