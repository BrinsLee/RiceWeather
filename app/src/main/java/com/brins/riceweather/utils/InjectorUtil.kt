package com.brins.riceweather.utils

import com.brins.riceweather.data.PlaceRepository
import com.brins.riceweather.data.WeatherRepository
import com.brins.riceweather.data.database.DatabaseFactory
import com.brins.riceweather.data.network.RiceWeatherNetwork
import com.brins.riceweather.ui.MainModelFactory
import com.brins.riceweather.ui.weather.WeatherModelFactory


object InjectorUtil {

    private fun getPlaceRepository() = PlaceRepository.getInstance(RiceWeatherNetwork.getInstance())

    private fun getWeatherRepository() = WeatherRepository.getInstance(
        DatabaseFactory.getWeatherDatabaseHelper(),
        RiceWeatherNetwork.getInstance()
    )

//    fun getChooseAreaModelFactory() = ChooseAreaModelFactory(getPlaceRepository())

    fun getWeatherModelFactory() = WeatherModelFactory(getWeatherRepository())

    fun getMainModelFactory() = MainModelFactory(getWeatherRepository())

}