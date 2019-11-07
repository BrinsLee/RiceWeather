package com.brins.riceweather.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.brins.riceweather.R
import com.brins.riceweather.data.model.weather.Forecast
import com.brins.riceweather.data.model.weather.Weather
import com.brins.riceweather.ui.view.ForecastLineView
import com.bumptech.glide.Glide

@BindingAdapter("bind:loadBingPic")
fun ImageView.loadBingPic(url: String?) {
    if (url != null) Glide.with(context).load(url).into(this)
}

@BindingAdapter("bind:colorSchemeResources")
fun SwipeRefreshLayout.colorSchemeResources(resId: Int) {
    setColorSchemeResources(resId)
}

@BindingAdapter("bind:showMax")
fun TextView.showMax(weather : Weather?) = weather?.let{
    text = "${it.forecastList[0].temperature.max}°"
}

@BindingAdapter("bind:showMin")
fun TextView.showMin(weather : Weather?) = weather?.let{
    text = "${it.forecastList[0].temperature.min}°"
}

@BindingAdapter("bind:forecastData")
fun ForecastLineView.forecastData(forecastList : List<Forecast>?) = forecastList?.let {
    forecastLists = forecastList
    invalidate()
}

@BindingAdapter("bind:background")
fun ViewGroup.setWeatherBackground(@DrawableRes background : Int){
    setBackgroundResource(background)
}