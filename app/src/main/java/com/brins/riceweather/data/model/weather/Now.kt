package com.brins.riceweather.data.model.weather

import com.google.gson.annotations.SerializedName

class Now {
    @SerializedName("fl")
    var feel = ""
    fun feel() = "$feel°"

    @SerializedName("tmp")
    var temperature = ""
    @SerializedName("cond")
    lateinit var more: More

    fun degree() = "$temperature℃"

    @SerializedName("wind_dir")
    var windDir = ""

    @SerializedName("wind_sc")
    var windSc = ""
    fun windSc() = "$windSc 级"
    @SerializedName("hum")
    var humidity = ""

    @SerializedName("pcpn")
    var percipitation = ""
    inner class More {
        @SerializedName("txt")
        var info = ""
    }
}