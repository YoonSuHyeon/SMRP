package com.example.smrp.home;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Response {


    @SerializedName("weather") List<Weather_item> list;

        public List<Weather_item> getweatherList() {
            return list;
        }
        @SerializedName("main")Weather_main weather_main;

    public Weather_main getWeather_main() {
        return weather_main;
    }
/*Weather_main weather_main;
    public Weather_main getWeather_main() {
        return weather_main;
    }*/

}
