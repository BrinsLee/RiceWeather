package com.brins.riceweather.data.model.place

import com.google.gson.annotations.SerializedName

class County (@SerializedName("name") val countyName: String, @SerializedName("weather_id") val weatherId: String){
    @Transient val id = 0
    var cityId = 0
}