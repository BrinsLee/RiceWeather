package com.brins.riceweather.data.network.api

import com.brins.riceweather.data.model.weather.HeWeather
import com.brins.riceweather.utils.weatherAppId
import com.brins.riceweather.utils.weatherAppSecret
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("api/")
    fun getWeather(@Query("appid") appId: String = weatherAppId,
                   @Query("appsecret") appSecret: String = weatherAppSecret,
                   @Query("version") version: String = "v1",
                   @Query("city") city: String): Call<HeWeather>

}