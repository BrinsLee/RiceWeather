package com.brins.riceweather.data.model.weather

import androidx.annotation.DrawableRes
import androidx.annotation.NonNull
import androidx.room.*
import com.brins.riceweather.R
import com.brins.riceweather.utils.Converter
import com.brins.riceweather.utils.map
import com.brins.riceweather.utils.weatherMap
import com.google.gson.annotations.SerializedName

@Entity(tableName = "weather")
@TypeConverters(Converter::class)
class Weather {
    @ColumnInfo(name = "ID")
    @PrimaryKey(autoGenerate = true)
    @NonNull
    var mId: Int = 0

    @SerializedName("day")
    @ColumnInfo(name = "day")
    var day: String = ""

    @SerializedName("date")
    @ColumnInfo(name = "date")
    var date: String = ""

    @SerializedName("week")
    @ColumnInfo(name = "week")
    var week: String = ""

    fun week() = "周${week.last()}"

    @SerializedName("wea")
    @ColumnInfo(name = "weather")
    var weatherDesc: String = ""

    @SerializedName("wea_img")
    @ColumnInfo(name = "weaImg")
    var weaImg: String = ""

    @SerializedName("air")
    @ColumnInfo(name = "air")
    var air: Int = 0

    @SerializedName("humidity")
    @ColumnInfo(name = "humidity")
    var humidity: String = ""

    @SerializedName("air_level")
    var airLevel: String = ""

    @SerializedName("air_tip")
    @ColumnInfo(name = "airTip")
    var airTip: String = ""

    @SerializedName("tem1")
    @ColumnInfo(name = "maxTemp")
    var maxTemp = ""

    fun maxTemp() = "${maxTempInt()}°"

    fun maxTempInt() = maxTemp.substring(0, maxTemp.lastIndex).toInt()

    @SerializedName("tem2")
    @ColumnInfo(name = "minTemp")
    var minTemp = ""

    fun minTemp() = "${minTempInt()}°"

    fun minTempInt() = minTemp.substring(0, minTemp.lastIndex).toInt()


    @SerializedName("tem")
    @ColumnInfo(name = "temperature")
    var temperature = ""

    fun temp() = "${tempInt()}°"

    fun tempInt() = temperature.substring(0, temperature.lastIndex).toInt()

    @SerializedName("win")
    @ColumnInfo(name = "winDis")
    lateinit var windDis: List<String>

    @SerializedName("win_speed")
    @ColumnInfo(name = "winSpeed")
    var windSpeed = ""

    fun windDesc() = "${windDis[0]} $windSpeed"

    @SerializedName("hours")
    @ColumnInfo(name = "hourForecast")
    lateinit var hourForecast: MutableList<HourForecast>

    @SerializedName("index")
    @ColumnInfo(name = "index")
    lateinit var index : List<Index>

    @DrawableRes
    @Ignore
    fun weatherDrawable(): Int {
        return if (map[weaImg] == null) R.drawable.bg_sunny else map[weaImg]!!
    }

    @DrawableRes
    fun weatherImages(): Int {
        return if (weatherMap[weaImg] == null) R.drawable.ic_weather_sun_sunny else weatherMap[weaImg]!!
    }
}