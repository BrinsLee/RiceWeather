package com.brins.riceweather.data.model.weather

import com.google.gson.annotations.SerializedName

class Index {

    @SerializedName("title")
    var title = ""

    @SerializedName("level")
    var level = ""

    @SerializedName("desc")
    var desc = ""

}