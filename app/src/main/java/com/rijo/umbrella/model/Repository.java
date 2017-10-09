package com.rijo.umbrella.model;

import com.rijo.umbrella.model.forcast.HourlyForecast;
import com.rijo.umbrella.util.Utilities;
import com.rijo.umbrella.model.condition.Conditions;
import com.rijo.umbrella.model.forcast.Forcast;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


/**
 * Created by rijogeorge on 10/8/17.
 */

public class Repository {
    public Weather fetchWeatherData(String zip) {
        Weather weather=null;
        Conditions conditions=null;
        Forcast forcast=null;
        conditions=getConditionsfromUrl(Const.CONDITION_API+zip+".json");
        forcast=getForcastfromUrl(Const.FORCAST_API+zip+".json");
        if(conditions!=null&&forcast!=null) {
            weather=createWeatherClass(conditions,forcast);
        }
        return weather;
    }

    private Forcast getForcastfromUrl(String forcastApi) {
        String forcastString= Utilities.downloadJsonFromUrl(forcastApi);
        if(forcastString!=null) {
            Forcast forcast = Utilities.getClassFromJSONString(forcastString, Forcast.class);
            return forcast;
        }
        else {
            return null;
        }
    }

    private Conditions getConditionsfromUrl(String conditionApi) {
        String imagesString= Utilities.downloadJsonFromUrl(conditionApi);
        if(imagesString!=null) {
            Conditions condition = Utilities.getClassFromJSONString(imagesString, Conditions.class);
            return condition;
        }
        else {
            return null;
        }
    }

    private Weather createWeatherClass(Conditions conditions, Forcast forcast) {
        Weather weather=new Weather();
        weather.setLocation(conditions.getCurrentObservation().getDisplayLocation().getFull());
        weather.setWeatherStatus(conditions.getCurrentObservation().getWeather());
        weather.setTempF(conditions.getCurrentObservation().getTempF());
        weather.setTempC(conditions.getCurrentObservation().getTempC());
        Date dt=Calendar.getInstance().getTime();
        int today=dt.getDate();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, 1);
        dt = c.getTime();
        int tomorrow=dt.getDate();
        c.add(Calendar.DATE, 1);
        dt = c.getTime();
        int dayAftTomorrow=dt.getDate();

        for(HourlyForecast hf : forcast.getHourlyForecast()) {
            HourlyWeather hw=new HourlyWeather();
            hw.setDate(new Date(Integer.parseInt(hf.getFCTTIME().getYear()),Integer.parseInt(hf.getFCTTIME().getMon()),Integer.parseInt(hf.getFCTTIME().getMday())));
            hw.setHour(hf.getFCTTIME().getHour());
            hw.setImageUrl(hf.getIconUrl());
            hw.setTempF(hf.getTemp().getMetric());
            hw.setTempC(hf.getTemp().getEnglish());
            weather.getHourlyWeather().add(hw);
            if(Integer.parseInt(hf.getFCTTIME().getMday()) == today){
                weather.getDay1().add(hw);
            }
            else if(Integer.parseInt(hf.getFCTTIME().getMday()) == tomorrow){
                weather.getDay2().add(hw);
            }
            else if(Integer.parseInt(hf.getFCTTIME().getMday()) == dayAftTomorrow){
                weather.getDay3().add(hw);
            }

        }
        return weather;
    }

}
