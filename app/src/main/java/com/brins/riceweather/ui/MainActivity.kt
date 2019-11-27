package com.brins.riceweather.ui

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.brins.riceweather.R
import com.brins.riceweather.databinding.ActivityMainBinding
import com.brins.riceweather.ui.weather.WeatherViewModel
import com.brins.riceweather.utils.InjectorUtil
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private val mainViewModel by lazy {
        ViewModelProviders.of(
            this,
            InjectorUtil.getMainModelFactory()
        ).get(MainViewModel::class.java)
    }
    private val weatherViewModel by lazy {
        ViewModelProviders.of(
            this,
            InjectorUtil.getWeatherModelFactory()
        ).get(WeatherViewModel::class.java)
    }

    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(
            this,
            R.layout.activity_main
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = weatherViewModel
        binding.lifecycleOwner = this
        setStatusBarTranslucent()
        appBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, p1 ->
            run {
                if (!swipeRefresh.isRefreshing) {
                    swipeRefresh.isEnabled = nested.scrollY == p1
                }
            }
        })
/*        nested.viewTreeObserver.addOnScrollChangedListener {
            swipeRefresh.isEnabled = nested.scrollY == 0
        }*/
        if (mainViewModel.isWeatherCached()){
            weatherViewModel.getWeatherFromDatabase()
        }else {
            weatherViewModel.getWeatherData()
        }
    }

    override fun getOffsetView(): View? {
        return titleView
    }
}
