package com.helder.section35_tasks.service.remote

import com.helder.section35_tasks.service.constant.RetrofitConstants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient private constructor() {
    companion object {
        private lateinit var INSTANCE: Retrofit
        private var token = ""
        private var personKey = ""

        private fun getRetrofitClient(): Retrofit {
            val httpClient = OkHttpClient().newBuilder()

            httpClient.addInterceptor { chain ->
                val request = chain.request()
                    .newBuilder()
                    .addHeader(RetrofitConstants.RequestHeaders.TOKEN, token)
                    .addHeader(RetrofitConstants.RequestHeaders.PERSON_KEY, personKey)
                    .build()
                chain.proceed(request)
            }

            synchronized(RetrofitClient::class.java) {
                if (!::INSTANCE.isInitialized) {
                    INSTANCE = Retrofit.Builder()
                        .baseUrl("http://devmasterteam.com/CursoAndroidAPI/")
                        .client(httpClient.build())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                }
            }

            return INSTANCE
        }

        fun <T> getService(serviceClass: Class<T>): T {
            return getRetrofitClient().create(serviceClass)
        }

        fun addHeaders(token: String, personKey: String) {
            this.token = token
            this.personKey = personKey
        }

        fun removeHeaders() {
            this.token = ""
            this.personKey = ""
        }
    }
}