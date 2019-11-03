package com.brins.riceweather.data

import com.brins.riceweather.data.model.weather.Weather
import com.brins.riceweather.data.network.RiceWeatherNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherRepository private constructor(val network : RiceWeatherNetwork) {
    companion object{
        private lateinit var instance : WeatherRepository

        fun getInstance(network: RiceWeatherNetwork): WeatherRepository{
            if (!::instance.isInitialized){
                synchronized(WeatherRepository::class.java){
                    if (!::instance.isInitialized){
                        this.instance = WeatherRepository(network)
                    }
                }
            }
            return this.instance
        }
    }

    suspend fun getWeather(weatherId : String): Weather {
        var weather = requestWeather(weatherId)
        return weather
    }

    private suspend fun requestWeather(weatherId: String) = withContext(Dispatchers.IO){
        val heWeather = network.fetchWeather(weatherId)
        val weather = heWeather.weather!![0]
        weather
    }
}