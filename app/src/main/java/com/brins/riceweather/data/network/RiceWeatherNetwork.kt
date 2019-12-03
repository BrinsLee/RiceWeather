package com.brins.riceweather.data.network

import com.brins.riceweather.utils.await

class RiceWeatherNetwork {

    companion object {
        private var instance: RiceWeatherNetwork? = null

        fun getInstance(): RiceWeatherNetwork {
            if (instance == null) {
                synchronized(RiceWeatherNetwork::class.java) {
                    if (instance == null) {
                        instance = RiceWeatherNetwork()
                    }
                }
            }
            return instance!!
        }
    }

    private val placeService = ApiHelper.getPlaceService()
    private val weatherService = ApiHelper.getWeatherService()

    suspend fun fetchWeather(location: String) = weatherService.getWeather(city = location).await()

}