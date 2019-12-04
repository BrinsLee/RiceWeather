package com.brins.riceweather.ui.weather

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brins.riceweather.RiceWeatherApplication
import com.brins.riceweather.data.WeatherRepository
import com.brins.riceweather.data.model.weather.HeWeather
import com.brins.riceweather.data.model.weather.Index
import com.brins.riceweather.data.model.weather.Weather
import kotlinx.coroutines.launch

class WeatherViewModel(private val repository: WeatherRepository) : ViewModel() {

    var metaWeather = MutableLiveData<HeWeather>()
    var weather = MutableLiveData<Weather>()
    var isRefreshed = MutableLiveData<Boolean>()
    var index = MutableLiveData<List<Index>>()
    var weatherId = ""

    /***网络请求天气数据*/
    fun getWeatherData(location: String) {
        weatherId = location
        isRefreshed.value = true
        launch({
            val data = repository.getWeather(weatherId)
            metaWeather.value = data
            weather.value = data.weather?.get(0)
            index.value = weather.value?.index
            isRefreshed.value = false
        }, {
            Toast.makeText(RiceWeatherApplication.context, it.message, Toast.LENGTH_SHORT).show()
            isRefreshed.value = false
        })
    }

    fun onRefresh() {
        getWeatherData(weatherId)
    }

    /***数据库获取天气数据*/
    fun getWeatherFromDatabase() {
        launch({
            val data = repository.getWeather()
            metaWeather.value = data
            weather.value = data.weather?.get(0)
            index.value = weather.value?.index
            weatherId = data.city
        }, {
            Toast.makeText(RiceWeatherApplication.context, it.message, Toast.LENGTH_SHORT).show()
        })
    }

    private fun launch(block: suspend () -> Unit, error: suspend (Throwable) -> Unit) =
        viewModelScope.launch {
            try {
                block()
            } catch (e: Throwable) {
                error(e)
            }
        }
}