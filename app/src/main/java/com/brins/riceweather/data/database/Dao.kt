package com.brins.riceweather.data.database
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.brins.riceweather.data.model.weather.HeWeather
import com.brins.riceweather.data.model.weather.Now
import com.brins.riceweather.data.model.weather.Weather
import io.reactivex.Single

@Dao
interface Dao {
    @Insert
    fun addWeather(weather: HeWeather)

    @Query("select * from metadata order by DataId DESC")
    fun getWeather(): HeWeather

    @Query("delete from metadata where DataId=:id")
    fun deleteWeather(id : Int)
}