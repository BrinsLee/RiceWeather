package com.brins.riceweather.data.model.weather

import com.brins.riceweather.R
import com.brins.riceweather.utils.weatherMap
import com.google.gson.annotations.SerializedName

class HourForecast {

    @SerializedName("day")
    var day = ""

    fun day() = "${day.substring(3, day.lastIndex)}:00"

    @SerializedName("wea")
    var wea = ""

    @SerializedName("tem")
    var temperature = ""

    fun temp() = "${tempInt()}°"

    fun tempInt() = temperature.substring(0, temperature.lastIndex).toInt()

    @SerializedName("win")
    var windDirection = ""

    @SerializedName("win_speed")
    var windSpeed = ""

    fun weatherImages() : Int {
        return if (weatherMap[wea] == null) R.drawable.ic_weather_sun_sunny else weatherMap[wea]!!
    }
}