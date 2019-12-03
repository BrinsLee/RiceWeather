package com.brins.riceweather.ui

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.brins.riceweather.R
import com.brins.riceweather.databinding.ActivityMainBinding
import com.brins.riceweather.ui.place.PlaceViewModel
import com.brins.riceweather.ui.weather.WeatherViewModel
import com.brins.riceweather.utils.InjectorUtil
import com.google.android.material.appbar.AppBarLayout
import com.tencent.map.geolocation.TencentLocation
import com.tencent.map.geolocation.TencentLocationListener
import kotlinx.android.synthetic.main.activity_main.*
import android.content.pm.PackageManager
import android.Manifest.permission
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.Manifest.permission.READ_PHONE_STATE
import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.os.Build
import android.util.Log


class MainActivity : BaseActivity(), TencentLocationListener {

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

    private val placeViewModel by lazy {
        ViewModelProviders.of(
            this
            , InjectorUtil.getPlaceModelFactory()
        ).get(PlaceViewModel::class.java)
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
    }

    override fun onStart() {
        super.onStart()
//        initLocationInfo()
        if (mainViewModel.isWeatherCached()) {
            weatherViewModel.getWeatherFromDatabase()
        } else {
            weatherViewModel.getWeatherData()
        }
    }

    /***初始化位置信息*/
    private fun initLocationInfo() {
        if (Build.VERSION.SDK_INT >= 23) {
            val permissions = arrayOf(
                ACCESS_COARSE_LOCATION,
                READ_PHONE_STATE,
                WRITE_EXTERNAL_STORAGE
            )

            if (checkSelfPermission(permissions[0]) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(permissions, 0)
            } else {
                placeViewModel.fetchPlaceInfo(this)
            }
        } else {
            placeViewModel.fetchPlaceInfo(this)
        }
    }

    override fun getOffsetView(): View? {
        return titleView
    }

    //Tencent定位服务回调
    override fun onStatusUpdate(p0: String?, p1: Int, p2: String?) {

    }

    override fun onLocationChanged(p0: TencentLocation?, p1: Int, p2: String?) {
/*        Log.d(TAG, "$p1")
        Log.d(TAG, p2)

        p0?.let {
            Log.d(TAG, it.address)
        }*/
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        placeViewModel.fetchPlaceInfo(this)

    }
}
