package com.brins.riceweather.data.database

import android.content.Context
import androidx.room.Room
import com.brins.riceweather.data.model.weather.Weather

class DatabaseHelper(context: Context) {
    private val appDatabase = Room.databaseBuilder(
        context,
        Database::class.java, "dbWeather"
        ).allowMainThreadQueries().build()

    fun insertWeather(weather: Weather) {
        return appDatabase.dao().addWeather(weather)
    }

    fun getWeather(): Weather? {
        return appDatabase.dao().getWeather()
    }

    fun deleteWeather(id: Int) {
        return appDatabase.dao().deleteWeather(id)
    }

}