package com.helder.section35_tasks.ui.viewmodel

import android.app.Application
import android.util.Log
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
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BaseViewModel(application: Application) : AndroidViewModel(application) {

    private val taskRepository = TaskRepository()
    private val priorityRepository = PriorityRepository(application.applicationContext)

    private val _tasks = MutableStateFlow<List<TaskModel>>(mutableListOf())
    val tasks = _tasks.asStateFlow()

    private val _receivedPriorities = MutableStateFlow<List<PriorityModel>>(mutableListOf())
    val receivedPriorities: StateFlow<List<PriorityModel>> = _receivedPriorities.asStateFlow()

    private val _wasTaskUpdated = MutableLiveData<ValidationModel>()
    val wasTaskUpdated: LiveData<ValidationModel> = _wasTaskUpdated

    fun getAllTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            taskRepository.getAllTasks(object : APIListener<List<TaskModel>> {
                override fun onSuccess(result: List<TaskModel>) {
                    Log.d("TASKS_FETCHING", result.toString())
                    _tasks.value = result
                }

                override fun onFailure(message: String) {
                    Log.d("TASKS_FETCHING", message)
                }
            })
        }
    }

    fun getNextSevenDaysTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            taskRepository.getDueInSevenDaysTasks(object : APIListener<List<TaskModel>> {
                override fun onSuccess(result: List<TaskModel>) {
                    Log.d("TASKS_FETCHING", result.toString())
                    _tasks.value = result
                }

                override fun onFailure(message: String) {
                    Log.d("TASKS_FETCHING", message)
                }

            })
        }
    }

    fun getOverdueTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            taskRepository.overdueTasks(object : APIListener<List<TaskModel>> {
                override fun onSuccess(result: List<TaskModel>) {
                    Log.d("TASKS_FETCHING", result.toString())
                    _tasks.value = result
                }

                override fun onFailure(message: String) {
                    Log.d("TASKS_FETCHING", message)
                }
            })
        }
    }

    fun createTask(task: TaskModel) {
        taskRepository.createTask(task, object : APIListener<Boolean> {
            override fun onSuccess(result: Boolean) {
                Log.d("TASKS_FETCHING", result.toString())
            }

            override fun onFailure(message: String) {
                Log.d("TASKS_FETCHING", message)
            }
        })
    }

    fun updateTask(task: TaskModel) {
        viewModelScope.launch(Dispatchers.IO) {
            taskRepository.updateTask(task, object : APIListener<Boolean> {
                override fun onSuccess(result: Boolean) {
                    Log.d("UPDATE_TASK", "Was task updated? $result")
                    _wasTaskUpdated.value = ValidationModel()
                }

                override fun onFailure(message: String) {
                    Log.d("UPDATE_TASK", "Task was not updated")
                    _wasTaskUpdated.value = ValidationModel(message)
                }
            })
        }
    }

    fun markComplete(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            taskRepository.markComplete(id, object : APIListener<Boolean> {
                override fun onSuccess(result: Boolean) {
                    Log.d("UPDATE_TASK", "Was task updated? $result")
                    _wasTaskUpdated.value = ValidationModel()
                }

                override fun onFailure(message: String) {
                    Log.d("UPDATE_TASK", "Task was not updated")
                    _wasTaskUpdated.value = ValidationModel(message)
                }
            })
        }
    }

    fun markIncomplete(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            taskRepository.markIncomplete(id, object : APIListener<Boolean> {
                override fun onSuccess(result: Boolean) {
                    Log.d("UPDATE_TASK", "Was task updated? $result")
                    _wasTaskUpdated.value = ValidationModel()
                }

                override fun onFailure(message: String) {
                    Log.d("UPDATE_TASK", "Task was not updated")
                    _wasTaskUpdated.value = ValidationModel(message)
                }
            })
        }
    }

    fun getPriorities() {
        viewModelScope.launch(Dispatchers.IO) {
            priorityRepository.getPriorities(object : APIListener<List<PriorityModel>> {
                override fun onSuccess(result: List<PriorityModel>) {
                    Log.d("PRIORITIES", result.toString())
                    _receivedPriorities.value = result
                }

                override fun onFailure(message: String) {
                    Log.d("PRIORITIES", message)
                }
            })
        }
    }

}