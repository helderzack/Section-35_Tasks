package com.helder.section35_tasks.ui.viewmodel

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.helder.section35_tasks.R
import com.helder.section35_tasks.data.model.PersonModel
import com.helder.section35_tasks.data.model.ValidationModel
import com.helder.section35_tasks.service.SecurityPreferences
import com.helder.section35_tasks.service.constant.RetrofitConstants
import com.helder.section35_tasks.service.listener.APIListener
import com.helder.section35_tasks.service.remote.RetrofitClient
import com.helder.section35_tasks.service.repository.PersonRepository

class LoginViewModel(private val application: Application) : AndroidViewModel(application) {

    private val repository = PersonRepository()
    private val securityPreferences = SecurityPreferences(application.applicationContext)

    private val _login = MutableLiveData<ValidationModel>()
    val login: LiveData<ValidationModel> = _login

    private val _loggedUser = MutableLiveData<Boolean>()
    val loggedUser: LiveData<Boolean> = _loggedUser

    fun doLogin(email: String, password: String) {
        repository.doLogin(email, password, object : APIListener<PersonModel> {
            override fun onSuccess(result: PersonModel) {
                securityPreferences.store(RetrofitConstants.RequestHeaders.TOKEN, result.token)
                securityPreferences.store(
                    RetrofitConstants.RequestHeaders.PERSON_KEY,
                    result.personKey
                )
                securityPreferences.store(RetrofitConstants.RequestHeaders.NAME, result.name)

                RetrofitClient.addHeaders(result.token, result.personKey)

                _login.value = ValidationModel()
            }

            override fun onFailure(message: String) {
                _login.value = ValidationModel(message)
            }
        })
    }

    fun verifyLoggedUser() {
        val token = securityPreferences.get(RetrofitConstants.RequestHeaders.TOKEN)
        val personKey = securityPreferences.get(RetrofitConstants.RequestHeaders.PERSON_KEY)

        // If user is not logged in, empty strings will be passed as headers
        RetrofitClient.addHeaders(token, personKey)

        _loggedUser.value = (token != "" && personKey != "")
    }
}