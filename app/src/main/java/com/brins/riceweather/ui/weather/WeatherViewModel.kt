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
import com.brins.riceweather.data.model.weather.WeatherDetail
import com.brins.riceweather.utils.*
import kotlinx.coroutines.launch

class WeatherViewModel(private val repository: WeatherRepository) : ViewModel() {

    var metaWeather = MutableLiveData<HeWeather>()
    var weather = MutableLiveData<Weather>()
    var isRefreshed = MutableLiveData<Boolean>()
    var index = MutableLiveData<List<Index>>()
    var weatherDetail = MutableLiveData<WeatherDetail>()
    var weatherId = ""
    var currentTime: Long = SpUtils.obtain(FILE_COMMON, RiceWeatherApplication.context).getLong(KEY_TIME, 0)

    /***网络请求天气数据*/
    fun getWeatherData(location: String) {
        weatherId = location
        isRefreshed.value = true
        launch({
            val data = repository.getWeather(weatherId)
            val data2 = repository.getWeatherDetail(weatherId)
            metaWeather.value = data
            weather.value = data.weather?.get(0)
            index.value = weather.value?.index
            weatherDetail.value = data2
            isRefreshed.value = false
            currentTime = System.currentTimeMillis()
            SpUtils.obtain(FILE_COMMON, RiceWeatherApplication.context).save(KEY_TIME, currentTime)
        }, {
            Toast.makeText(RiceWeatherApplication.context, it.message, Toast.LENGTH_SHORT).show()
            isRefreshed.value = false
        })

    }

    fun onRefresh() {
        if (isThreeHour(currentTime, System.currentTimeMillis())){
            getWeatherData(weatherId)

        }else{
            Toast.makeText(RiceWeatherApplication.context,"天气数据每三小时刷新一次，请勿频繁刷新。",Toast.LENGTH_SHORT).show()
            isRefreshed.value = false
            return
        }
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

    /***数据库获取天气详情数据*/
    fun getWeatherDetailDromDatabase() {
        launch({
            val data = repository.getWeatherDetail()
            weatherDetail.value = data
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