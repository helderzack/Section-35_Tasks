package com.helder.section35_tasks.service

import android.content.Context
import com.helder.section35_tasks.R

class SecurityPreferences(context: Context) {


    private val sharedPreferences = context.getSharedPreferences(
        context.getString(R.string.app_shared_preferences),
        Context.MODE_PRIVATE
    )

    fun store(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun get(key: String): String {
        return sharedPreferences.getString(key, "") ?: ""
    }

    fun remove(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }

}