package com.helder.section35_tasks.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.helder.section35_tasks.data.model.PriorityModel
import com.helder.section35_tasks.data.model.TaskModel
import com.helder.section35_tasks.data.model.ValidationModel
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

    private var _task = MutableLiveData<TaskModel>()
    var task: LiveData<TaskModel> = _task

    private val _taskAddedSuccessfully = MutableLiveData<ValidationModel>()
    val taskAddedSuccessfully: LiveData<ValidationModel> = _taskAddedSuccessfully

    private val _wasTaskUpdated = MutableLiveData<ValidationModel>()
    val wasTaskUpdated: LiveData<ValidationModel> = _wasTaskUpdated

    fun getTask(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            taskRepository.getTask(id, object : APIListener<TaskModel> {
                override fun onSuccess(result: TaskModel) {
                    _task.value = result
                }

                override fun onFailure(message: String) {
                    TODO("Not yet implemented")
                }
            })
        }
    }

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
        viewModelScope.launch(Dispatchers.IO) {
            taskRepository.createTask(task, object : APIListener<Boolean> {
                override fun onSuccess(result: Boolean) {
                    _taskAddedSuccessfully.value = ValidationModel()
                }

                override fun onFailure(message: String) {
                    _taskAddedSuccessfully.value = ValidationModel(message)
                }
            })
        }
    }

    fun updateTask(task: TaskModel) {
        viewModelScope.launch(Dispatchers.IO) {
            taskRepository.updateTask(task, object : APIListener<Boolean> {
                override fun onSuccess(result: Boolean) {
                    _wasTaskUpdated.value = ValidationModel()
                }

                override fun onFailure(message: String) {
                    _wasTaskUpdated.value = ValidationModel(message)
                }
            })
        }
    }

}