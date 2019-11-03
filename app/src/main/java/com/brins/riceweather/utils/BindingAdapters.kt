package com.brins.riceweather.utils

import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.brins.riceweather.R
import com.brins.riceweather.data.model.weather.Weather
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
fun TextView.showMax(weather : Weather){
    text = "${weather.forecastList[0].temperature.max}°"
}

@BindingAdapter("showMin")
fun TextView.showMin(weather : Weather){
    text = "${weather.forecastList[0].temperature.min}°"
}