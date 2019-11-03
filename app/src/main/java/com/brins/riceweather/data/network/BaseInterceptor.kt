package com.brins.riceweather.data.network

import android.util.Log
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.Response
import okio.Buffer
import java.io.IOException
import java.nio.charset.Charset

class BaseInterceptor(var logTag: String) :Interceptor {

    companion object{
        private val MEDIA_TYPE_FORM =
            MediaType.parse("application/x-www-form-urlencoded; charset=UTF-8")
        private val UTF_8 = Charset.forName("UTF-8")
    }
    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
        val jsonParser = JsonParser()
        var requestInfo: JsonElement? = null
        val requestBody = bodyToString(request.body())
        requestInfo = jsonParser.parse(requestBody)
        val requestJson = JsonObject()
        requestJson.add("info", requestInfo)
        val requestStr = requestJson.toString()

        Log.i(logTag, "=====Request===== ${request.url()}")
        Log.i(logTag, requestStr)
        var response = chain.proceed(request)
        return response
    }


    private fun bodyToString(request: RequestBody?): String {
        try {
            val buffer = Buffer()
            if (request != null)
                request.writeTo(buffer)
            else
                return ""
            return buffer.readUtf8()
        } catch (e: IOException) {
            return "did not work"
        }

    }
}