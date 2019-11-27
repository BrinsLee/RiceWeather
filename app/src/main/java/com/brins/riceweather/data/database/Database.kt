package com.brins.riceweather.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.brins.riceweather.data.model.weather.AQI
import com.brins.riceweather.data.model.weather.Basic
import com.brins.riceweather.data.model.weather.Now
import com.brins.riceweather.data.model.weather.Weather

@Database(
    entities = [Weather::class, AQI::class, Basic::class, Now::class],
    version = 1,
    exportSchema = false
)
abstract class Database : RoomDatabase() {

    abstract fun dao(): Dao
}