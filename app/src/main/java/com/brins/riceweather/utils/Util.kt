package com.brins.riceweather.utils

import android.os.Handler
import android.os.Message
import android.util.Log
import androidx.room.TypeConverter
import com.brins.riceweather.R
import com.brins.riceweather.RiceWeatherApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.lang.ref.WeakReference
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import android.content.Context
import android.os.Build
import android.util.DisplayMetrics
import android.view.WindowManager
import com.brins.riceweather.data.model.weather.HourForecast
import com.brins.riceweather.data.model.weather.Index
import com.brins.riceweather.data.model.weather.Weather
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Field

val weatherAppId = "25511893"
val weatherAppSecret = "PvauxcX5"

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

//Now.more 转换器
class Converter {

    @TypeConverter
    fun revertWeather(forecastlist: String): List<Weather>? {
        try {
            val type = object : TypeToken<List<Weather>>() {}.type
            return Gson().fromJson(forecastlist, type)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    @TypeConverter
    fun convertWeather(list: List<Weather>): String {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun revertHourForecast(hourForecast: String): List<HourForecast>? {
        try {
            val type = object : TypeToken<List<HourForecast>>() {}.type
            return Gson().fromJson(hourForecast, type)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    @TypeConverter
    fun convertHourForecast(list: List<HourForecast>): String {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun revertIndex(index: String): List<Index>? {
        try {
            val type = object : TypeToken<List<Index>>() {}.type
            return Gson().fromJson(index, type)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    @TypeConverter
    fun converIndex(list: List<Index>): String {
        val gson = Gson()
        return gson.toJson(list)
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

val WEATHER_SUNNY = "qing"
val WEATHER_CLOUNDY = "yun"
val WEATHER_OVERCAST = "yin"
val WEATHER_RAIN = "yu"
val WEATHER_FOG = "wu"
val WEATHER_SNOW = "xue"
val WEATHER_THUNDER = "lei"
val WEATHER_DUST = "shachen"


val map =
    mapOf(
        WEATHER_SUNNY to R.drawable.bg_sunny,
        WEATHER_OVERCAST to R.drawable.bg_haze,
        WEATHER_CLOUNDY to R.drawable.bg_haze
    )

val weatherMap =
    mapOf(
        WEATHER_SUNNY to R.drawable.ic_weather_sun_sunny,
        WEATHER_CLOUNDY to R.drawable.ic_weather_sun_cloudy,
        WEATHER_OVERCAST to R.drawable.ic_weather_cloudy,
        WEATHER_RAIN to R.drawable.ic_weather_rain
    )


/**
 * 设备工具，提供了获取设备屏幕显示的各种指标（如高度、宽度等）、尺寸单位之间的转换以获取设备版本型号等方法
 */
object DeviceUtils {

    private var statusBarHeight: Int = 0

    val screenWidthPx: Int
        get() = getScreenWidthPx(RiceWeatherApplication.context)

    val screenHeightPx: Int
        get() = getScreenHeightPx(RiceWeatherApplication.context)

    val screenWidthDp: Int
        get() = getScreenWidthDp(RiceWeatherApplication.context)

    val screenHeightDp: Int
        get() = getScreenHeightDp(RiceWeatherApplication.context)

    val screenDensity: Float
        get() = getScreenDensity(RiceWeatherApplication.context)

    val screenScaleDensity: Float
        get() = getScreenScaleDensity(RiceWeatherApplication.context)

    val screenDensityDpi: Int
        get() = getScreenDensityDpi(RiceWeatherApplication.context)

    val screenSize: Float
        get() = getScreenSize(RiceWeatherApplication.context)

    val densityMode: String
        get() = getDensityMode(RiceWeatherApplication.context)

    val screenMode: String
        get() = getScreenMode(RiceWeatherApplication.context)

    val displayMetrics: DisplayMetrics
        get() = getDisplayMetrics(RiceWeatherApplication.context)

    val realScreenHeightPx: Int
        get() = getRealScreenHeightPx(RiceWeatherApplication.context)

    /**
     * 获取设备版本
     */
    val osVersion: String
        get() = Build.VERSION.RELEASE

    /**
     * 获取设备型号
     */
    val deviceModel: String
        get() = Build.MODEL

    /**
     * 获取设备屏幕的宽度（单位为px）
     */
    fun getScreenWidthPx(context: Context): Int {
        return getDisplayMetrics(context).widthPixels
    }

    /**
     * 获取设备屏幕的高度（单位为px）
     */
    fun getScreenHeightPx(context: Context): Int {
        return getDisplayMetrics(context).heightPixels
    }

    /**
     * 获取设备屏幕的宽度（单位为dp）
     */
    fun getScreenWidthDp(context: Context): Int {
        return (getScreenWidthPx(context) / getScreenDensity(context)).toInt()
    }

    /**
     * 获取设备屏幕的高度（单位为dp）
     */
    fun getScreenHeightDp(context: Context): Int {
        return (getScreenHeightPx(context) / getScreenDensity(context)).toInt()
    }

    /**
     * 获取设备屏幕的密度比例，如2.0
     */
    fun getScreenDensity(context: Context): Float {
        return getDisplayMetrics(context).density
    }


    /**
     * 获取设备屏幕的伸缩密度
     */
    fun getScreenScaleDensity(context: Context): Float {
        return getDisplayMetrics(context).scaledDensity
    }

    /**
     * 获取设备屏幕的密度大小，如320（对应屏幕密度比例为2.0）
     */
    fun getScreenDensityDpi(context: Context): Int {
        return getDisplayMetrics(context).densityDpi
    }

    /**
     * 获取设备屏幕对角线尺寸, 3.0以上系统会减去StatusBar的高度
     */
    fun getScreenSize(context: Context): Float {
        val metrics = getDisplayMetrics(context)
        val diagonalPx = Math.sqrt(
            Math.pow(
                metrics.widthPixels.toDouble(),
                2.0
            ) + Math.pow(metrics.heightPixels.toDouble(), 2.0)
        )
        return (diagonalPx / metrics.densityDpi).toFloat()
    }

    /**
     * 获取设备屏幕的密度模式，如"ldpi"、"mdpi"、"hdpi"等
     */
    fun getDensityMode(context: Context): String {
        val densityDpi = getScreenDensityDpi(context)
        when (densityDpi) {
            120 -> return "ldpi"
            160 -> return "mdpi"
            240 -> return "hdpi"
            320 -> return "xdpi"
            480 -> return "xxdpi"
            640 -> return "xxxdpi"
            else -> return "Unknown"
        }
    }

    /**
     * 获取屏幕的大小模式，如"xlarge"、"large"、"normal"等
     */
    fun getScreenMode(context: Context): String {
        val screenWidthDp = getScreenWidthDp(context)
        val screenHeightDp = getScreenHeightDp(context)

        return if (screenHeightDp >= 960 && screenWidthDp >= 720) {
            "xlarge"
        } else if (screenHeightDp >= 640 && screenWidthDp >= 480) {
            "large"
        } else if (screenHeightDp >= 470 && screenWidthDp >= 320) {
            "normal"
        } else if (screenHeightDp >= 426 && screenWidthDp >= 320) {
            "small"
        } else {
            "unknown"
        }
    }

    /**
     * 获取当前显示指标
     */
    fun getDisplayMetrics(context: Context): DisplayMetrics {
        return context.resources.displayMetrics
    }

    /**
     * 获取设备屏幕显示高度，不包括状态栏高度
     */
    fun getRealScreenHeightPx(context: Context): Int {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        try {
            val c = Class.forName("android.view.Display")
            val method = c.getMethod("getRealHeight")
            return method.invoke(display) as Int
        } catch (e: Exception) {
            e.printStackTrace()
            return getScreenHeightPx(context)
        }

    }

    /**
     * 根据设备分辨率从dp转成px
     */
    fun dip2px(context: Context, dpValue: Float): Int {
        val scale = getScreenDensity(context)
        return (dpValue * scale + 0.5f).toInt()
    }

    fun dip2px(dpValue: Float): Int {
        return dip2px(RiceWeatherApplication.context, dpValue)
    }

    /**
     * 根据设备分辨率从px转成dp
     */
    fun px2dip(context: Context, pxValue: Float): Int {
        val scale = getScreenDensity(context)
        return (pxValue / scale + 0.5f).toInt()
    }

    fun px2dip(pxValue: Float): Int {
        return px2dip(RiceWeatherApplication.context, pxValue)
    }

    /**
     * 根据设备分辨率从dp转成px（float）
     */
    fun dip2pxF(context: Context, dpValue: Float): Float {
        val scale = getScreenDensity(context)
        return dpValue * scale + 0.5f
    }

    fun dip2pxF(dpValue: Float): Float {
        return dip2pxF(RiceWeatherApplication.context, dpValue)
    }

    /**
     * 根据设备分辨率从sp转成px（float）
     */
    fun sp2pxF(context: Context, spValue: Float): Float {
        val scale = getScreenScaleDensity(context)
        return spValue * scale + 0.5f
    }

    fun sp2pxF(spValue: Float): Float {
        return sp2pxF(RiceWeatherApplication.context, spValue)
    }

    @JvmOverloads
    fun getStatusBarHeight(context: Context = RiceWeatherApplication.context): Int {
        if (statusBarHeight <= 0) {
            var c: Class<*>? = null
            var obj: Any? = null
            var field: Field? = null
            var x = 0
            try {
                c = Class.forName("com.android.internal.R\$dimen")
                obj = c!!.newInstance()
                field = c.getField("status_bar_height")
                x = Integer.parseInt(field!!.get(obj).toString())
                statusBarHeight = context.resources.getDimensionPixelSize(
                    x
                )
            } catch (e1: Exception) {
                e1.printStackTrace()
            }

        }
        return statusBarHeight
    }

}