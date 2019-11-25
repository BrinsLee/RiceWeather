package com.brins.riceweather.data.database
import androidx.room.Insert
import com.brins.riceweather.data.model.weather.Now
import io.reactivex.Single

interface Dao {
    @Insert
    fun addWeather(weather: Now) : Single<Int>

    @Insert
    fun addWeatherForecast()
}