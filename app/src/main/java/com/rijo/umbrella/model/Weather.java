package com.rijo.umbrella.model;

import com.rijo.umbrella.model.forcast.HourlyForecast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rijogeorge on 10/8/17.
 */

public class Weather {
    private String location;
    private String weatherStatus;
    private Double tempF;
    private Double tempC;
    private List<HourlyWeather> hourlyWeather = new ArrayList<>();
    private List<HourlyWeather> day1 = new ArrayList<>();
    private List<HourlyWeather> day2 = new ArrayList<>();
    private List<HourlyWeather> day3 = new ArrayList<>();

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getWeatherStatus() {
        return weatherStatus;
    }

    public void setWeatherStatus(String weatherStatus) {
        this.weatherStatus = weatherStatus;
    }

    public Double getTempF() {
        return tempF;
    }

    public void setTempF(Double tempF) {
        this.tempF = tempF;
    }

    public Double getTempC() {
        return tempC;
    }

    public void setTempC(Double tempC) {
        this.tempC = tempC;
    }

    public List<HourlyWeather> getHourlyWeather() {
        return hourlyWeather;
    }

    public void setHourlyWeather(List<HourlyWeather> hourlyWeather) {
        this.hourlyWeather = hourlyWeather;
    }

    public List<HourlyWeather> getDay1() {
        return day1;
    }

    public void setDay1(List<HourlyWeather> day1) {
        this.day1 = day1;
    }

    public List<HourlyWeather> getDay2() {
        return day2;
    }

    public void setDay2(List<HourlyWeather> day2) {
        this.day2 = day2;
    }

    public List<HourlyWeather> getDay3() {
        return day3;
    }

    public void setDay3(List<HourlyWeather> day3) {
        this.day3 = day3;
    }
}
