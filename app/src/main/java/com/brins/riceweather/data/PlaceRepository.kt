package com.brins.riceweather.data

import com.brins.riceweather.data.network.RiceWeatherNetwork

class PlaceRepository(private val network: RiceWeatherNetwork) {

    companion object{
        private lateinit var instance : PlaceRepository

        fun getInstance(network: RiceWeatherNetwork): PlaceRepository{
            if (!::instance.isInitialized){
                synchronized(PlaceRepository::class.java){
                    if (!::instance.isInitialized){
                        this.instance = PlaceRepository(network)
                    }
                }
            }
            return this.instance
        }
    }
}