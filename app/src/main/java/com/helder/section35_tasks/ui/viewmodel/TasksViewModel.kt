package com.helder.section35_tasks.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.helder.section35_tasks.data.model.TaskModel
import com.helder.section35_tasks.service.listener.APIListener
import com.helder.section35_tasks.service.repository.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TasksViewModel(application: Application) : AndroidViewModel(application) {

    private val taskRepository = TaskRepository()

    private val _tasks = MutableStateFlow<List<TaskModel>>(mutableListOf())
    val tasks = _tasks.asStateFlow()

    fun getAllTasks() {
        taskRepository.getAllTasks(object : APIListener<List<TaskModel>> {
            override fun onSuccess(result: List<TaskModel>) {
                Log.d("TASKS_FETCHING", result.size.toString())
                _tasks.value = result
            }

            override fun onFailure(message: String) {
                Log.d("TASKS_FETCHING", message)
            }

        })
    }

    fun getNextSevenDaysTasks() {
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

    fun getOverdueTasks() {
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

}