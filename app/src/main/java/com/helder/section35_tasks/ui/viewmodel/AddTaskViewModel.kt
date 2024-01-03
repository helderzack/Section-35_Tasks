package com.helder.section35_tasks.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.helder.section35_tasks.data.model.PriorityModel
import com.helder.section35_tasks.service.listener.APIListener
import com.helder.section35_tasks.service.repository.PriorityRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AddTaskViewModel(application: Application) : AndroidViewModel(application) {

    private val priorityRepository = PriorityRepository(application.applicationContext)

    private val _priorities = MutableStateFlow<List<PriorityModel>>(mutableListOf())
    val priorities = _priorities.asStateFlow()

    fun getPriorities() {
        viewModelScope.launch(Dispatchers.IO) {
            priorityRepository.getPriorities(object : APIListener<List<PriorityModel>> {
                override fun onSuccess(result: List<PriorityModel>) {
                    _priorities.value = result
                }

                override fun onFailure(message: String) {
                    _priorities.value = mutableListOf()
                }
            })
        }
    }
}