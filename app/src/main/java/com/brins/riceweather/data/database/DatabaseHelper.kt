package com.brins.riceweather.data.database

import android.content.Context
import androidx.room.Room
import com.brins.riceweather.data.model.weather.HeWeather
import com.brins.riceweather.data.model.weather.WeatherDetail

class DatabaseHelper(context: Context) {
    private val appDatabase = Room.databaseBuilder(
        context,
        Database::class.java, "dbWeather"
        ).allowMainThreadQueries().build()

    fun insertWeather(weather: HeWeather) {
        return appDatabase.dao().addWeather(weather)
    }

    fun getWeather(): HeWeather? {
        return appDatabase.dao().getWeather()
    }

    fun deleteWeather(id: Int) {
        return appDatabase.dao().deleteWeather(id)
    }

    fun insertWeatherDetail(weatherDetail: WeatherDetail) {
        return appDatabase.dao().addWeatherDetail(weatherDetail)
    }

    fun getWeatherDetail(): WeatherDetail? {
        return appDatabase.dao().getWeatherDetail()
    }

}