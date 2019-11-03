package com.brins.riceweather.data.network

import com.brins.riceweather.data.network.api.PlaceService
import com.brins.riceweather.data.network.api.WeatherService

object ApiHelper {

    private const val BASE_URL = "http://guolin.tech/"
    private var mPlaceService: PlaceService? = null
    private var mWeatherService: WeatherService? = null

    fun getPlaceService(): PlaceService {
        if (mPlaceService == null) {
            synchronized(PlaceService::class.java) {
                if (mPlaceService == null) {
                    mPlaceService =
                        RetrofitFactory.newRetrofit(BASE_URL).create(PlaceService::class.java)
                }
            }
        }
        return mPlaceService!!
    }

    fun getWeatherService(): WeatherService {
        if (mWeatherService == null) {
            synchronized(WeatherService::class.java) {
                if (mWeatherService == null) {
                    mWeatherService =
                        RetrofitFactory.newRetrofit(BASE_URL).create(WeatherService::class.java)
                }
            }
        }
        return mWeatherService!!
    }
}