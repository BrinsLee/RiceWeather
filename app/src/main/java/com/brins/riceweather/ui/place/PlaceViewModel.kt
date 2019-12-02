package com.brins.riceweather.ui.place

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.brins.riceweather.data.PlaceRepository
import com.tencent.map.geolocation.TencentLocationListener

class PlaceViewModel(private val placeRepository: PlaceRepository): ViewModel() {

    var place = MutableLiveData<String>()

    /***请求定位信息*/
    fun fetchPlaceInfo(listener: TencentLocationListener){
        placeRepository.getLocationInfo(listener)
    }

}