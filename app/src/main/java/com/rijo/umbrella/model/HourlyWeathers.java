package com.rijo.umbrella.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by rijogeorge on 10/9/17.
 */

public class HourlyWeathers implements Parcelable {
    ArrayList<HourlyWeather> hourlyWeather=new ArrayList<>();

    protected HourlyWeathers(Parcel in) {
        hourlyWeather = in.createTypedArrayList(HourlyWeather.CREATOR);
    }
    public HourlyWeathers() {

    }

    public static final Creator<HourlyWeathers> CREATOR = new Creator<HourlyWeathers>() {
        @Override
        public HourlyWeathers createFromParcel(Parcel in) {
            return new HourlyWeathers(in);
        }

        @Override
        public HourlyWeathers[] newArray(int size) {
            return new HourlyWeathers[size];
        }
    };

    public ArrayList<HourlyWeather> getHourlyWeather() {
        return hourlyWeather;
    }

    public void setHourlyWeather(ArrayList<HourlyWeather> hourlyWeather) {
        this.hourlyWeather = hourlyWeather;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(hourlyWeather);
    }
}
