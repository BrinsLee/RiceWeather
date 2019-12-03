package com.brins.riceweather.data.model.weather

import androidx.annotation.NonNull
import androidx.room.*
import com.brins.riceweather.utils.Converter
import com.google.gson.annotations.SerializedName
//https://www.tianqiapi.com/?action=v1
@Entity(tableName = "metadata")
@TypeConverters(Converter::class)
class HeWeather {

    @ColumnInfo(name = "DataId")
    @PrimaryKey(autoGenerate = true)
    @NonNull
    var mId: Int = 0

    @SerializedName("cityid")
    @ColumnInfo(name = "cityId")
    var cityId = ""

    @SerializedName("update_time")
    @ColumnInfo(name = "updateTime")
    var updateTime = ""

    fun time() = updateTime.split(" ")[1].substring(0, 5)


    @SerializedName("city")
    @ColumnInfo(name = "city")
    var city = ""

    @Ignore
    fun city() = "${city}区"

    @SerializedName("cityEn")
    @ColumnInfo(name = "cityEn")
    var cityEn = ""

    @SerializedName("country")
    @ColumnInfo(name = "country")
    var country = ""

    @SerializedName("countryEn")
    @ColumnInfo(name = "countryEn")
    var countryEn = ""


    @SerializedName("data")
    var weather: List<Weather>? = null

}