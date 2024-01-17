package com.helder.section35_tasks.service.repository

import com.google.gson.Gson
import com.helder.section35_tasks.service.constant.Constants
import com.helder.section35_tasks.service.listener.APIListener
import retrofit2.Response

open class BaseRepository {

    private fun failResponse(errorBody: String): String {
        return Gson().fromJson(errorBody, String::class.java)
    }

    protected fun <T> handleResponse(
        response: Response<T>,
        listener: APIListener<T>
    ) {
        if (response.code() == Constants.HTTPStatusCode.OK) {
            response.body()?.let { listener.onSuccess(it) }
        } else {
            val errorMessage = failResponse(response.errorBody()!!.string())
            listener.onFailure(errorMessage)
        }
    }

}