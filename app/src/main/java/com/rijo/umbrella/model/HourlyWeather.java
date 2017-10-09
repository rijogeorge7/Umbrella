package com.rijo.umbrella.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by rijogeorge on 10/8/17.
 */

public class HourlyWeather implements Parcelable{
    private Date date;
    private String hour;
    private String tempF;
    private String tempC;
    private String imageUrl;

    protected HourlyWeather(Parcel in) {
        hour = in.readString();
        tempF = in.readString();
        tempC = in.readString();
        imageUrl = in.readString();
    }
    public HourlyWeather() {

    }

    public static final Creator<HourlyWeather> CREATOR = new Creator<HourlyWeather>() {
        @Override
        public HourlyWeather createFromParcel(Parcel in) {
            return new HourlyWeather(in);
        }

        @Override
        public HourlyWeather[] newArray(int size) {
            return new HourlyWeather[size];
        }
    };

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getTempF() {
        return tempF;
    }

    public void setTempF(String tempF) {
        this.tempF = tempF;
    }

    public String getTempC() {
        return tempC;
    }

    public void setTempC(String tempC) {
        this.tempC = tempC;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(hour);
        parcel.writeString(tempF);
        parcel.writeString(tempC);
        parcel.writeString(imageUrl);
    }
}
