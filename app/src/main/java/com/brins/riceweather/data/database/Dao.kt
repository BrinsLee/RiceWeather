package com.brins.riceweather.data.database
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.brins.riceweather.data.model.weather.Now
import com.brins.riceweather.data.model.weather.Weather
import io.reactivex.Single

@Dao
interface Dao {
    @Insert
    fun addWeather(weather: Weather)

    @Query("select * from weather order by ID DESC")
    fun getWeather(): Weather

    @Query("delete from weather where ID=:id")
    fun deleteWeather(id : Int)
}