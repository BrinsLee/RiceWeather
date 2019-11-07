package com.brins.riceweather.data.model.weather

import androidx.annotation.DrawableRes
import com.brins.riceweather.R
import com.brins.riceweather.utils.map
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

    @SerializedName("cond_code")
    var condCode = ""

    @DrawableRes
    fun weatherDrawable(): Int {
        return if (map[condCode] == null) R.drawable.bg_sunny else map[condCode]!!
    }

    inner class More {
        @SerializedName("txt")
        var info = ""

        @SerializedName("code")
        var code = ""
        /*
        晴 100
        阴 104
        多云 101
        */
    }
}