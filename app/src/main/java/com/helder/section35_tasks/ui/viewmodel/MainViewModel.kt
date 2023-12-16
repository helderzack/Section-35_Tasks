package com.helder.section35_tasks.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.helder.section35_tasks.data.model.ValidationModel
import com.helder.section35_tasks.service.SecurityPreferences
import com.helder.section35_tasks.service.constant.RetrofitConstants
import com.helder.section35_tasks.service.remote.RetrofitClient

class MainViewModel(private val application: Application): AndroidViewModel(application) {

    private var securityPreferences = SecurityPreferences(application.applicationContext)

    private val _logout = MutableLiveData(false)
    val logout: LiveData<Boolean> = _logout

    fun doLogout() {
        securityPreferences.remove(RetrofitConstants.RequestHeaders.TOKEN)
        securityPreferences.remove(RetrofitConstants.RequestHeaders.PERSON_KEY)
        securityPreferences.remove(RetrofitConstants.RequestHeaders.NAME)

        RetrofitClient.removeHeaders()

        _logout.value = true
    }
}