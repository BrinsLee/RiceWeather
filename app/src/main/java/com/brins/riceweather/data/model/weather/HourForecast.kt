package com.brins.riceweather.data.model.weather

import com.google.gson.annotations.SerializedName

class HourForecast {

    @SerializedName("day")
    var day = ""

    @SerializedName("wea")
    var wea = ""

    @SerializedName("tem")
    var temperature = ""

    @SerializedName("win")
    var windDirection = ""

    @SerializedName("win_speed")
    var windSpeed = ""
}