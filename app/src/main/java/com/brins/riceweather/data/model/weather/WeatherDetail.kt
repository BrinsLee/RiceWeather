package com.brins.riceweather.data.model.weather

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.regex.Pattern

@Entity(tableName = "weatherDetail")
class WeatherDetail {

    @ColumnInfo(name = "ID")
    @PrimaryKey(autoGenerate = true)
    @NonNull
    var mId: Int = 0

    @SerializedName("win")
    @ColumnInfo(name = "win")
    var win = ""

    @SerializedName("win_speed")
    @ColumnInfo(name = "windSpeed")
    var windSpeed = ""

    @SerializedName("win_meter")
    @ColumnInfo(name = "windMeter")
    var windMeter = ""

    @Ignore
    fun windMeter(): Int {
        if (windMeter.isEmpty()){
            return 0
        }
        val pattern = Pattern.compile("[^0-9]")
        val m = pattern.matcher(windMeter)
        return m.replaceAll("").trim().toInt()
    }

    @SerializedName("humidity")
    @ColumnInfo(name = "humidity")
    var humidity = ""

    @Ignore
    fun humidity() = humidity.substring(0,humidity.lastIndex).toInt()

    @SerializedName("visibility")
    @ColumnInfo(name = "visibility")
    var visibility = ""

    @SerializedName("pressure")
    @ColumnInfo(name = "pressure")
    var pressure = ""

    @Ignore
    fun pressure() = pressure.toFloat() / 10

    @SerializedName("air")
    @ColumnInfo(name = "air")
    var air = ""

    @SerializedName("air_pm25")
    @ColumnInfo(name = "airPm25")
    var pm25 = ""

    @Ignore
    fun pm25() = pm25.toFloat()

    @SerializedName("air_level")
    @ColumnInfo(name = "airLevel")
    var level = ""
}