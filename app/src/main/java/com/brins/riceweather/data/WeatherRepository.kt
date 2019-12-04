package com.brins.riceweather.data

import com.brins.riceweather.data.database.DatabaseHelper
import com.brins.riceweather.data.model.weather.HeWeather
import com.brins.riceweather.data.model.weather.Weather
import com.brins.riceweather.data.model.weather.WeatherDetail
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
    suspend fun getWeather(weatherId: String): HeWeather {
        val weather = requestWeather(weatherId)
        return weather
    }

    /***拉取天气信息*/
    private suspend fun requestWeather(location: String) = withContext(Dispatchers.IO) {
        val heWeather = network.fetchWeather(location)
        heWeather.weather?.let {
            if (it[0].hourForecast.size < 8) {
                for (i in 0..2) {
                    it[0].hourForecast.add(heWeather.weather!![1].hourForecast[i])
                }
            }
        }
        weatherDao.insertWeather(heWeather)
        heWeather
    }

    /***获取缓存在数据库的天气*/
    suspend fun getWeather() : HeWeather {
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



    suspend fun getWeatherDetail(): WeatherDetail{
        val weatherDetail = requestWeatherDetail()
        return weatherDetail!!
    }

    private suspend fun requestWeatherDetail() = withContext(Dispatchers.IO){
        val weatherDetail = weatherDao.getWeatherDetail()
        weatherDetail
    }

    /***网络获取天气详情数据*/
    suspend fun getWeatherDetail(weatherId: String): WeatherDetail {
        val weather = requestWeatherDetail(weatherId)
        return weather
    }

    /***拉取天气详情信息*/
    private suspend fun requestWeatherDetail(location: String) = withContext(Dispatchers.IO) {
        val weatherDetail = network.fetchWeatherDetail(location)
        weatherDao.insertWeatherDetail(weatherDetail)
        weatherDetail
    }

}