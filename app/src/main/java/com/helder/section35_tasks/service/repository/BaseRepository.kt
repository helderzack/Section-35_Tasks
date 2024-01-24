package com.helder.section35_tasks.service.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.google.gson.Gson
import com.helder.section35_tasks.R
import com.helder.section35_tasks.service.constant.Constants
import com.helder.section35_tasks.service.listener.APIListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class BaseRepository(val context: Context) {

    private fun isConnectionAvailable(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val networkCapabilities =
            connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

        return when {
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    }

    protected fun <T> executeCall(call: Call<T>, listener: APIListener<T>) {
        if(!isConnectionAvailable()) {
            listener.onFailure(context.getString(R.string.ERROR_INTERNET_CONNECTION))
            return
        }

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