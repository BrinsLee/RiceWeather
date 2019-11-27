package com.brins.riceweather.data

import com.brins.riceweather.data.database.DatabaseHelper
import com.brins.riceweather.data.model.weather.Weather
import com.brins.riceweather.data.network.RiceWeatherNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherRepository private constructor(
    val weatherDao: DatabaseHelper,
    val network: RiceWeatherNetwork
) {
    companion object {
        private lateinit var instance: WeatherRepository

        fun getInstance(weatherDao: DatabaseHelper, network: RiceWeatherNetwork): WeatherRepository {
            if (!::instance.isInitialized) {
                synchronized(WeatherRepository::class.java) {
                    if (!::instance.isInitialized) {
                        this.instance = WeatherRepository(weatherDao, network)
                    }
                }
            }
            return this.instance
        }
    }

    /***网络获取天气数据*/
    suspend fun getWeather(weatherId: String): Weather {
        val weather = requestWeather(weatherId)
        return weather
    }

    private suspend fun requestWeather(weatherId: String) = withContext(Dispatchers.IO) {
        val heWeather = network.fetchWeather(weatherId)
        val weather = heWeather.weather!![0]
        weatherDao.insertWeather(weather)
        weather
    }
    /***获取缓存在数据库的天气*/
    suspend fun getWeather() : Weather {
        val weather = requestWeather()
        return weather!!
    }

    private suspend fun requestWeather() = withContext(Dispatchers.IO){
        val weather = weatherDao.getWeather()
        weather
    }


    /***判断是否缓存天气数据*/
    fun isWeatherCached(): Boolean {
        return weatherDao.getWeather() != null
    }
}