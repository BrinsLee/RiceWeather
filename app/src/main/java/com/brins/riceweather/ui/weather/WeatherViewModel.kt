package com.brins.riceweather.ui.weather

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brins.riceweather.RiceWeatherApplication
import com.brins.riceweather.data.WeatherRepository
import com.brins.riceweather.data.model.weather.Weather
import kotlinx.coroutines.launch

class WeatherViewModel(private val repository: WeatherRepository): ViewModel() {

    var weather = MutableLiveData<Weather>()
    var weatherId = "CN101280101"

    fun getWeatherData(){
        launch({
            weather.value = repository.getWeather(weatherId)

        },{
            Toast.makeText(RiceWeatherApplication.context, it.message, Toast.LENGTH_SHORT).show()

        })
    }

    private fun launch(block: suspend () -> Unit, error: suspend (Throwable) -> Unit) = viewModelScope.launch {
        try {
            block()
        } catch (e: Throwable) {
            error(e)
        }
    }
}