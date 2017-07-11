package ru.gdgkazan.simpleweather.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * @author Artur Vasilov
 */
public class City implements Serializable {

    @SerializedName("name")
    private String mName;

    @SerializedName("id")
    private String mID;

    @SerializedName("weather")
    private List<Weather> mWeathers;

    @SerializedName("main")
    private Main mMain;

    @SerializedName("wind")
    private Wind mWind;

    public City() {
    }

    public City(@NonNull String name) {
        mName = name;
    }

    public City(@NonNull String name, @NonNull String id) {
        mName = name;
        mID   = id;
    }
    @NonNull
    public String getName() {
        return mName;
    }

    public void setName(@NonNull String name) {
        mName = name;
    }

    @Nullable
    public Weather getWeather() {
        if (mWeathers == null || mWeathers.isEmpty()) {
            return null;
        }
        return mWeathers.get(0);
    }

    @Nullable
    public void setWeather(List<Weather> weather) {
        mWeathers = weather;
    }

    @Nullable
    public Main getMain() {
        return mMain;
    }

    @Nullable
    public Wind getWind() {
        return mWind;
    }

    @Nullable
    public void setWind(Wind wind) {
        mWind = wind;
    }

    @Nullable
    public void setMain(Main main) {
        mMain = main;
    }

    @Nullable
    public String getID() {
        return mID;
    }





}
