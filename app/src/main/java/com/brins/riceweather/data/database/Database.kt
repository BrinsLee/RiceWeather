package com.brins.riceweather.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

abstract class Database : RoomDatabase() {

    abstract fun dao() : Dao
}