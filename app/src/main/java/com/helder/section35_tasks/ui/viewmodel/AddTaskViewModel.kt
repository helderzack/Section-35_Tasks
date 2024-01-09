package com.helder.section35_tasks.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.helder.section35_tasks.data.model.PriorityModel
import com.helder.section35_tasks.data.model.TaskModel
import com.helder.section35_tasks.service.listener.APIListener
import com.helder.section35_tasks.service.repository.PriorityRepository
import com.helder.section35_tasks.service.repository.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AddTaskViewModel(application: Application) : AndroidViewModel(application) {

    private val priorityRepository = PriorityRepository(application.applicationContext)
    private val taskRepository = TaskRepository()

    private val _priorities = MutableStateFlow<List<PriorityModel>>(mutableListOf())
    val priorities = _priorities.asStateFlow()

    private val _taskAddedSuccessfully = MutableLiveData<Boolean>(false)
    val taskAddedSuccessfully: LiveData<Boolean> = _taskAddedSuccessfully

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

    fun createTask(task: TaskModel) {
        taskRepository.createTask(task, object : APIListener<Boolean> {
            override fun onSuccess(result: Boolean) {
                Log.d("CREATE_TASK", "Was Task created? $result")
                _taskAddedSuccessfully.value = result
            }

            override fun onFailure(message: String) {
                _taskAddedSuccessfully.value = false
            }
        })
    }
}