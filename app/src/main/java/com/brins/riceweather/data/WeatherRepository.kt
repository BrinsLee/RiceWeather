package com.brins.riceweather.data

class WeatherRepository {
    companion object{
        private lateinit var instance : WeatherRepository

        fun getInstance(): WeatherRepository{
            if (!::instance.isInitialized){
                synchronized(WeatherRepository::class.java){
                    if (!::instance.isInitialized){
                        instance = WeatherRepository()
                    }
                }
            }
            return instance
        }
    }
}