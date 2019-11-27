package com.brins.riceweather.data.model.weather

import androidx.annotation.NonNull
import androidx.room.*
import com.brins.riceweather.data.model.weather.*
import com.brins.riceweather.utils.Converter
import com.google.gson.annotations.SerializedName

@Entity(tableName = "weather")
@TypeConverters(Converter::class)
class Weather {
    @ColumnInfo(name = "ID")
    @PrimaryKey(autoGenerate = true)
    @NonNull
    var mId: Int = 0
    var status = ""
    @Embedded
    lateinit var basic: Basic
    @Embedded
    lateinit var aqi: AQI
    @Embedded
    lateinit var now: Now

    @Ignore
    lateinit var suggestion: Suggestion

    @SerializedName("daily_forecast")
    @ColumnInfo(name = "forecast")
    lateinit var forecastList: List<Forecast>
}