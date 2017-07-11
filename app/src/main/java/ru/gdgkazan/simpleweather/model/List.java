
package ru.gdgkazan.simpleweather.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class List {


    @SerializedName("weather")
    @Expose
    private java.util.List<Weather> weather = null;
    @SerializedName("main")
    @Expose
    private Main main;
    @SerializedName("visibility")
    @Expose
    private Integer visibility;
    @SerializedName("wind")
    @Expose
    private Wind wind;

    @SerializedName("dt")
    @Expose
    private Integer dt;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;

    public java.util.List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(java.util.List<Weather> weather) {
        this.weather = weather;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Integer getVisibility() {
        return visibility;
    }

    public void setVisibility(Integer visibility) {
        this.visibility = visibility;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Integer getDt() {
        return dt;
    }

    public void setDt(Integer dt) {
        this.dt = dt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
