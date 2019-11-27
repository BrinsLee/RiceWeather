package com.brins.riceweather.data.model.weather

import androidx.annotation.NonNull
import androidx.room.*

@Entity(tableName = "Aqi")
class AQI {

    @ColumnInfo(name = "aqi_id")
    @PrimaryKey(autoGenerate = true)
    @NonNull
    var mId: Int = 0
    @Embedded
    lateinit var city: AQICity

    class AQICity {
        @ColumnInfo(name = "aqicity_id")
        @PrimaryKey(autoGenerate = true)
        @NonNull
        var mId: Int = 0
        @ColumnInfo(name = "aqi")
        var aqi = ""
        @ColumnInfo(name = "pm")
        var pm25  = ""
        @Ignore
        var qlty = ""
    }
}