package com.brins.riceweather.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.brins.riceweather.R
import com.brins.riceweather.databinding.ActivityMainBinding
import com.brins.riceweather.ui.weather.WeatherViewModel
import com.brins.riceweather.utils.InjectorUtil
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private val viewModel by lazy {
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
        binding.viewModel = viewModel
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
        viewModel.getWeatherData()

    }

    override fun getOffsetView(): View? {
        return titleView
    }
}
