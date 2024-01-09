package com.helder.section35_tasks.service.remote

import com.google.gson.GsonBuilder
import com.helder.section35_tasks.service.adapter.LocalDateAdapter
import com.helder.section35_tasks.service.constant.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate

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
                    .addHeader(Constants.RequestHeaders.TOKEN, token)
                    .addHeader(Constants.RequestHeaders.PERSON_KEY, personKey)
                    .build()
                chain.proceed(request)
            }

            synchronized(RetrofitClient::class.java) {
                if (!::INSTANCE.isInitialized) {
                    INSTANCE = Retrofit.Builder()
                        .baseUrl("http://devmasterteam.com/CursoAndroidAPI/")
                        .client(httpClient.build())
                        .addConverterFactory(
                            GsonConverterFactory.create(
                                GsonBuilder()
                                    .registerTypeAdapter(LocalDate::class.java, LocalDateAdapter())
                                    .create()
                            )
                        )
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