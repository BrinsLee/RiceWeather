package com.brins.riceweather.data.model.weather

import androidx.annotation.DrawableRes
import androidx.annotation.NonNull
import androidx.room.*
import com.brins.riceweather.R
import com.brins.riceweather.utils.map
import com.google.gson.annotations.SerializedName

@Entity(tableName = "weather_now")
class Now {
    @ColumnInfo(name = "now_id")
    @PrimaryKey(autoGenerate = true)
    @NonNull
    var mId: Int = 0

    /*
    * 体感温度
    * */
    @SerializedName("fl")
    @ColumnInfo(name = "feel")
    var feel = ""

    @Ignore
    fun feel() = "$feel°"

    /*
    * 温度
    * */
    @SerializedName("tmp")
    @ColumnInfo(name = "temp")
    var temperature = ""


    @SerializedName("cond")
    @Embedded
    lateinit var more: More

    fun degree() = "$temperature℃"

    @SerializedName("wind_dir")
    var windDir = ""

    @SerializedName("wind_sc")
    @ColumnInfo(name = "wind")
    var windSc = ""
    @Ignore
    fun windSc() = "$windSc 级"

    @SerializedName("hum")
    @ColumnInfo(name = "hum")
    var humidity = ""

    @SerializedName("pcpn")
    var percipitation = ""

    @SerializedName("cond_code")
    var condCode = ""

    @DrawableRes
    @Ignore
    fun weatherDrawable(): Int {
        return if (map[condCode] == null) R.drawable.bg_sunny else map[condCode]!!
    }


    class More {
        @ColumnInfo(name = "more_id")
        @PrimaryKey(autoGenerate = true)
        @NonNull
        var mId: Int = 0
        @SerializedName("txt")
        @ColumnInfo(name = "info")
        var info = ""

        @ColumnInfo(name = "code")
        @SerializedName("code")
        var code = ""
        /*
        晴 100
        阴 104
        多云 101
        */
    }
}