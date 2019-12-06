package com.brins.riceweather.ui

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.brins.riceweather.R
import com.brins.riceweather.databinding.ActivityMainBinding
import com.brins.riceweather.ui.place.PlaceViewModel
import com.brins.riceweather.ui.weather.WeatherViewModel
import com.google.android.material.appbar.AppBarLayout
import com.tencent.map.geolocation.TencentLocation
import com.tencent.map.geolocation.TencentLocationListener
import kotlinx.android.synthetic.main.activity_main.*
import android.content.pm.PackageManager
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.Manifest.permission.READ_PHONE_STATE
import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.os.Build
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.Observable
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.brins.riceweather.data.model.weather.Index
import com.brins.riceweather.ui.adapter.CommonViewAdapter
import com.brins.riceweather.ui.adapter.ViewHolder
import com.brins.riceweather.utils.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


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

    private lateinit var mAdapter: CommonViewAdapter<Index>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        register(this)
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
        weatherViewModel.index.observe(this@MainActivity,
            Observer<List<Index>> {
                mAdapter = object : CommonViewAdapter<Index>(
                    this@MainActivity,
                    R.layout.item_daily_index,
                    it as ArrayList<Index>
                ) {
                    override fun converted(holder: ViewHolder, t: Index, position: Int) {
                        holder.getView<TextView>(R.id.title).text = it[position].title()
                        holder.getView<TextView>(R.id.level).text = it[position].level
                        holder.getView<TextView>(R.id.desc).text = it[position].desc

                    }

                }
                recycler.adapter = mAdapter
                recycler.layoutManager = LinearLayoutManager(this)
                recycler.addItemDecoration(DividerItemDecoration(this, LinearLayout.VERTICAL))
            })
        if (mainViewModel.isWeatherCached()) {
            weatherViewModel.getWeatherFromDatabase()
            weatherViewModel.getWeatherDetailDromDatabase()
        } else {
            initLocationInfo()
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
//        weatherViewModel.getWeatherData()
        Log.d(TAG, "$p1")
        Log.d(TAG, p2)

        p0?.let {
            Log.d(TAG, it.address)
            Log.d(TAG, it.cityCode)
            Log.d(TAG, it.cityPhoneCode)
            Log.d(TAG, it.district)
            Log.d(TAG, it.district.split("区")[0])
            weatherViewModel.getWeatherData(it.district.split("区")[0])

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        placeViewModel.fetchPlaceInfo(this)

    }

    override fun onDestroy() {
        super.onDestroy()
        unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMsgEvent(event: EventMsg<*>) {

        if (event.code == CODE_INIT_LOCATION) {
            initLocationInfo()
        }

    }
}
