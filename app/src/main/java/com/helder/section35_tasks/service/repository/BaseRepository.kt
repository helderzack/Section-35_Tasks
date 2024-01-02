package com.helder.section35_tasks.service.repository

import com.google.gson.Gson

open class BaseRepository {

    protected fun failResponse(errorBody: String): String {
        return Gson().fromJson(errorBody, String::class.java)
    }
}