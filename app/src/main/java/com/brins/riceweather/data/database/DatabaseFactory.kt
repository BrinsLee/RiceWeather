package com.brins.riceweather.data.database

import com.brins.riceweather.RiceWeatherApplication

object DatabaseFactory {
    private var mDatabaseHelper: DatabaseHelper? = null

    fun getWeatherDatabaseHelper(): DatabaseHelper {
        if (mDatabaseHelper == null) {
            synchronized(DatabaseHelper::class.java) {
                if (mDatabaseHelper == null) {
                    mDatabaseHelper = DatabaseHelper(RiceWeatherApplication.context)
                }
            }
        }
        return mDatabaseHelper!!
    }
}