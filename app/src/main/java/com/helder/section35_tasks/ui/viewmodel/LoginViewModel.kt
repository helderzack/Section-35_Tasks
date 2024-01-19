package com.helder.section35_tasks.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.helder.section35_tasks.data.model.PersonModel
import com.helder.section35_tasks.data.model.PriorityModel
import com.helder.section35_tasks.data.model.ValidationModel
import com.helder.section35_tasks.service.SecurityPreferences
import com.helder.section35_tasks.service.constant.Constants
import com.helder.section35_tasks.service.listener.APIListener
import com.helder.section35_tasks.service.remote.RetrofitClient
import com.helder.section35_tasks.service.repository.PersonRepository
import com.helder.section35_tasks.service.repository.PriorityRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = PersonRepository()
    private val priorityRepository = PriorityRepository(application.applicationContext)
    private val securityPreferences = SecurityPreferences(application.applicationContext)

    private val _login = MutableLiveData<ValidationModel>()
    val login: LiveData<ValidationModel> = _login

    private val _loggedUser = MutableLiveData<Boolean>()
    val loggedUser: LiveData<Boolean> = _loggedUser

    private val _receivedPriorities = MutableStateFlow<List<PriorityModel>>(mutableListOf())
    val receivedPriorities: StateFlow<List<PriorityModel>> = _receivedPriorities.asStateFlow()

    fun doLogin(email: String, password: String) {
        repository.doLogin(email, password, object : APIListener<PersonModel> {
            override fun onSuccess(result: PersonModel) {
                securityPreferences.store(Constants.RequestHeaders.TOKEN, result.token)
                securityPreferences.store(Constants.RequestHeaders.PERSON_KEY, result.personKey)
                securityPreferences.store(Constants.RequestHeaders.NAME, result.name)

                RetrofitClient.addHeaders(result.token, result.personKey)

                _login.value = ValidationModel()
            }

            override fun onFailure(message: String) {
                _login.value = ValidationModel(message)
            }
        })
    }

    fun verifyLoggedUser() {
        val token = securityPreferences.get(Constants.RequestHeaders.TOKEN)
        val personKey = securityPreferences.get(Constants.RequestHeaders.PERSON_KEY)

        // If user is not logged in, empty strings will be passed as headers
        RetrofitClient.addHeaders(token, personKey)

        _loggedUser.value = (token != "" && personKey != "")
    }

    fun getPriorities() {
        viewModelScope.launch {
            priorityRepository.getPriorities(object : APIListener<List<PriorityModel>> {
                override fun onSuccess(result: List<PriorityModel>) {
                    _receivedPriorities.value = result
                }

                override fun onFailure(message: String) {
                }
            })
        }
    }

    fun savePriorities(priorities: List<PriorityModel>) {
        viewModelScope.launch(Dispatchers.IO) {
            priorityRepository.savePriorities(priorities)
        }
    }
}