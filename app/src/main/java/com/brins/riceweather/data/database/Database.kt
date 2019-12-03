package com.brins.riceweather.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.brins.riceweather.data.model.weather.*

@Database(
    entities = [HeWeather::class],
    version = 2,
    exportSchema = false
)
abstract class Database : RoomDatabase() {

    abstract fun dao(): Dao
}