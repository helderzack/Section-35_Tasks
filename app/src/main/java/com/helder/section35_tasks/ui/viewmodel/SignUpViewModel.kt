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

class SignUpViewModel(private val application: Application) : AndroidViewModel(application) {

    private val repository = PersonRepository()
    private val securityPreferences = SecurityPreferences(application.applicationContext)

    private val _signUp = MutableLiveData<ValidationModel>()
    val signUp: LiveData<ValidationModel> = _signUp

    fun create(name: String, email: String, password: String) {
        repository.registerUser(name, email, password, object : APIListener<PersonModel> {
            override fun onSuccess(result: PersonModel) {
                securityPreferences.store(RetrofitConstants.RequestHeaders.TOKEN, result.token)
                securityPreferences.store(RetrofitConstants.RequestHeaders.PERSON_KEY, result.personKey)
                securityPreferences.store(RetrofitConstants.RequestHeaders.NAME, result.name)

                RetrofitClient.addHeaders(result.token, result.personKey)

                _signUp.value = ValidationModel()
            }

            override fun onFailure(message: String) {
                _signUp.value = ValidationModel(message)
            }
        })
    }
}