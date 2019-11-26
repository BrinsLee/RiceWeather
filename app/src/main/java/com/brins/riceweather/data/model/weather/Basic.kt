package com.brins.riceweather.data.model.weather

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "weather_basic")
class Basic {
    @ColumnInfo(name = "basic_id")
    @PrimaryKey(autoGenerate = true)
    @NonNull
    var mId: Int = 0

    @SerializedName("city")
    @ColumnInfo(name = "cityName")
    var cityName = ""

    @SerializedName("id")
    @ColumnInfo(name = "weatherId")
    var weatherId = ""

    @Embedded
    lateinit var update: Update

    class Update {
        @ColumnInfo(name = "update_id")
        @PrimaryKey(autoGenerate = true)
        @NonNull
        var mId: Int = 0

        @SerializedName("loc")
        @ColumnInfo(name = "updateTime")
        var updateTime = ""

        fun time() = updateTime.split(" ")[1]
    }
}