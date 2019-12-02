package com.brins.riceweather.data

import com.brins.riceweather.RiceWeatherApplication
import com.brins.riceweather.data.network.RiceWeatherNetwork
import com.tencent.map.geolocation.TencentLocationListener
import com.tencent.map.geolocation.TencentLocationManager
import com.tencent.map.geolocation.TencentLocationRequest
import com.tencent.map.geolocation.TencentLocationRequest.REQUEST_LEVEL_ADMIN_AREA
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PlaceRepository(private val network: RiceWeatherNetwork) {

    private val locationManager = TencentLocationManager.getInstance(RiceWeatherApplication.context)

    companion object {
        private lateinit var instance: PlaceRepository

        fun getInstance(network: RiceWeatherNetwork): PlaceRepository {
            if (!::instance.isInitialized) {
                synchronized(PlaceRepository::class.java) {
                    if (!::instance.isInitialized) {
                        this.instance = PlaceRepository(network)
                    }
                }
            }
            return this.instance
        }
    }


    fun getLocationInfo(listener: TencentLocationListener){
        locationManager.requestLocationUpdates(createRequest(), listener)
    }

    private fun createRequest(): TencentLocationRequest {
        val tencentLocationRequest = TencentLocationRequest.create()
        tencentLocationRequest.interval = 300000
        tencentLocationRequest.requestLevel = REQUEST_LEVEL_ADMIN_AREA
        return tencentLocationRequest
    }
}