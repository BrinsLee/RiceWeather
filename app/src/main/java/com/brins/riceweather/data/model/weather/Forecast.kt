package com.brins.riceweather.data.model.weather

import com.brins.riceweather.R
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

    fun getWeathreImage(): Int {
        return R.drawable.ic_icon_cloundy
    }

    fun date() = date.substring(5, date.length)
}