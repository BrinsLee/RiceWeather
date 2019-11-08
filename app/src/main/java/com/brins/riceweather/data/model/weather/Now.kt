package com.brins.riceweather.data.model.weather

import androidx.annotation.DrawableRes
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.brins.riceweather.R
import com.brins.riceweather.utils.map
import com.google.gson.annotations.SerializedName

@Entity(tableName = "weather_now")
class Now {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    @NonNull
    var mId: Int = 0

    /*
    * 体感温度
    * */
    @SerializedName("fl")
    @ColumnInfo(name = "feel")
    var feel = ""

    fun feel() = "$feel°"

    /*
    * 温度
    * */
    @SerializedName("tmp")
    @ColumnInfo(name = "temp")
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


    class More {
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