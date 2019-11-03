package com.brins.riceweather.data.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitFactory {

    companion object{
        private val TIMEOUT_CONNECTED: Long = 60
        private val TIMEOUT_READ: Long = 60
        private val TIMEOUT_WRITE: Long = 60

        fun newRetrofit(baseUrl: String): Retrofit {
            val retrofit = Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(getClient())
                .build()
            return retrofit
        }

        private fun getClient(): OkHttpClient {
            val builder: OkHttpClient.Builder = OkHttpClient().newBuilder()
            builder.connectTimeout(TIMEOUT_CONNECTED, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            return builder.build()
        }
    }

}