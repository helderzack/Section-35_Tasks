package com.helder.section35_tasks.service.repository

import com.google.gson.Gson
import com.helder.section35_tasks.service.constant.Constants
import com.helder.section35_tasks.service.listener.APIListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class BaseRepository {

    fun <T> executeCall(call: Call<T>, listener: APIListener<T>) {
        call.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.code() == Constants.HTTPStatusCode.OK) {
                    response.body()?.let { listener.onSuccess(it) }
                } else {
                    val errorMessage = failResponse(response.errorBody()!!.string())
                    listener.onFailure(errorMessage)
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                listener.onFailure("Error: ${t.message}")
            }
        })
    }

    private fun failResponse(errorBody: String): String {
        return Gson().fromJson(errorBody, String::class.java)
    }

}