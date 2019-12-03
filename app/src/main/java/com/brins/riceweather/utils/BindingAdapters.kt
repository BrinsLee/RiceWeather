package com.brins.riceweather.utils

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.brins.riceweather.data.model.weather.Forecast
import com.brins.riceweather.data.model.weather.Weather
import com.brins.riceweather.ui.view.ForecastLineView
import com.bumptech.glide.Glide

@BindingAdapter("bind:loadBingPic")
fun ImageView.loadBingPic(url: String?) {
    if (url != null) Glide.with(context).load(url).into(this)
}

@BindingAdapter("bind:weatherDrawable")
fun ImageView.loadWeatherIcon(forecast: Weather?) {
    if (forecast != null) setImageResource(forecast.weatherImages())

}

@BindingAdapter("bind:weatherIcon")
fun ImageView.loadWeatherIcon(@ColorRes drawable : Int) {
    setImageResource(drawable)
}

@BindingAdapter("bind:colorSchemeResources")
fun SwipeRefreshLayout.colorSchemeResources(resId: Int) {
    setColorSchemeResources(resId)
}

@BindingAdapter("bind:showMax")
fun TextView.showMax(weather: Weather?) = weather?.let {
    text = "${it.maxTemp}°"
}

@BindingAdapter("bind:showMin")
fun TextView.showMin(weather: Weather?) = weather?.let {
    text = "${it.minTemp}°"
}

@BindingAdapter("bind:forecastData")
fun ForecastLineView.forecastData(forecastList: List<Weather>?) = forecastList?.let {
    forecastLists = forecastList
    invalidate()
}

@BindingAdapter("bind:background")
fun ViewGroup.setWeatherBackground(@DrawableRes background: Int) {
    setBackgroundResource(background)
}