package com.brins.riceweather.ui.place

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.brins.riceweather.data.PlaceRepository

class PlaceModelFactory (private val placeRepository: PlaceRepository) : ViewModelProvider.NewInstanceFactory(){

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PlaceViewModel(placeRepository) as T
    }
}