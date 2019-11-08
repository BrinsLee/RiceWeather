package com.brins.riceweather.utils

import android.os.Handler
import android.os.Message
import android.util.Log
import com.brins.riceweather.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.ref.WeakReference
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


class WeakHandler(handler: IHandler) : Handler() {

    interface IHandler {
        fun handleMsg(msg: Message)
    }

    private val mRef: WeakReference<IHandler> = WeakReference(handler)

    override fun handleMessage(msg: Message?) {
        var handler = mRef.get()
        if (handler != null && msg != null) {
            handler.handleMsg(msg)
        }
    }
}

fun launch(block: suspend () -> Unit, error: suspend (Throwable) -> Unit) = CoroutineScope(
    Dispatchers.Main
).launch {
    try {
        block()
    } catch (e: Throwable) {
        error(e)
    }
}


suspend fun <T> Call<T>.await(): T {
    return suspendCoroutine { continuation ->
        enqueue(object : Callback<T> {
            override fun onFailure(call: Call<T>, t: Throwable) {
                continuation.resumeWithException(t)
            }

            override fun onResponse(call: Call<T>, response: Response<T>) {
                val body = response.body()
                Log.d("await", response.message())
                if (body != null) continuation.resume(body)
                else continuation.resumeWithException(RuntimeException("response body is null"))
            }
        })
    }
}

val WEATHER_SUNNY = "100"
val WEATHER_CLOUNDY = "101"
val WEATHER_OVERCAST = "104"

val map =
    mapOf(
        WEATHER_SUNNY to R.drawable.bg_sunny,
        WEATHER_OVERCAST to R.drawable.bg_haze,
        WEATHER_CLOUNDY to R.drawable.bg_haze
    )

val weatherMap =
    mapOf(
        "晴" to R.drawable.ic_weather_sunny,
        "多云" to R.drawable.ic_weather_cloundy,
        "阴" to R.drawable.ic_weather_cloundy,
        "小雨" to R.drawable.ic_weather_rain,
        "中雨" to R.drawable.ic_weather_rain,
        "大雨" to R.drawable.ic_weather_rain,
        "雷阵雨" to R.drawable.ic_weather_rain
        )