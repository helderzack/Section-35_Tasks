package com.helder.section35_tasks.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.helder.section35_tasks.service.SecurityPreferences
import com.helder.section35_tasks.service.constant.Constants
import com.helder.section35_tasks.service.remote.RetrofitClient

class MainViewModel(application: Application): AndroidViewModel(application) {

    private var securityPreferences = SecurityPreferences(application.applicationContext)

    private val _logout = MutableLiveData(false)
    val logout: LiveData<Boolean> = _logout

    fun doLogout() {
        securityPreferences.remove(Constants.RequestHeaders.TOKEN)
        securityPreferences.remove(Constants.RequestHeaders.PERSON_KEY)
        securityPreferences.remove(Constants.RequestHeaders.NAME)

        RetrofitClient.removeHeaders()

        _logout.value = true
    }
}