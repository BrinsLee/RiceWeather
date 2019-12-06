package com.brins.riceweather.data.model.weather

import com.google.gson.annotations.SerializedName

class Index {

    @SerializedName("title")
    var title = ""

    fun title(): String{
        return if (title.contains("</em>")){
            "健身指数"
        }else{
            title
        }
    }

    @SerializedName("level")
    var level = ""

    @SerializedName("desc")
    var desc = ""

}