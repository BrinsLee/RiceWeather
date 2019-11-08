package com.brins.riceweather.data.model.weather

import androidx.annotation.DrawableRes
import com.brins.riceweather.R
import com.brins.riceweather.utils.map
import com.brins.riceweather.utils.weatherMap
import com.google.gson.annotations.SerializedName

class Forecast {
    var date: String = ""
    @SerializedName("tmp")
    lateinit var temperature: Temperature
    @SerializedName("cond")
    lateinit var more: More

    inner class Temperature {
        var max = ""
        var min = ""

        fun max() = "$max ℃"
        fun min() = "$min ℃"

    }

    inner class More {
        @SerializedName("txt_d")
        var info = ""
    }

    @DrawableRes
    fun weatherImages(): Int {
        return if (weatherMap[more.info] == null) R.drawable.bg_sunny else weatherMap[more.info]!!
    }

    fun date() = date.substring(5, date.length)
}