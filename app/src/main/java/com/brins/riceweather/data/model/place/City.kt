package com.brins.riceweather.data.model.place

import com.google.gson.annotations.SerializedName

class City ( @SerializedName("name") val cityName: String, @SerializedName("id") val cityCode: Int) {
    @Transient val id = 0
    var provinceId = 0
}