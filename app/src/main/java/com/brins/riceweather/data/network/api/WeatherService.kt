package com.brins.riceweather.data.network.api

import com.brins.riceweather.data.model.weather.HeWeather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("api/weather")
    fun getWeather(@Query("cityid") weatherId: String): Call<HeWeather>

}