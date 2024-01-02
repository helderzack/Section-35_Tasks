package com.helder.section35_tasks.service.repository

import android.util.Log
import com.helder.section35_tasks.data.model.TaskModel
import com.helder.section35_tasks.service.constant.Constants
import com.helder.section35_tasks.service.listener.APIListener
import com.helder.section35_tasks.service.remote.RetrofitClient
import com.helder.section35_tasks.service.remote.TaskService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TaskRepository: BaseRepository() {

    private val taskService = RetrofitClient.getService(TaskService::class.java)

    fun getAllTasks(listener: APIListener<List<TaskModel>>) {
        taskService.getAllTasks().enqueue(object : Callback<List<TaskModel>> {
            override fun onResponse(
                call: Call<List<TaskModel>>,
                response: Response<List<TaskModel>>
            ) {
                if(response.code() == Constants.HTTPStatusCode.OK) {
                    response.body()?.let { listener.onSuccess(it) }
                } else {
                    val errorBody = failResponse(response.errorBody()!!.string())
                    Log.d("TASKS_FETCHING", errorBody)
                    listener.onFailure(errorBody)
                }
            }

            override fun onFailure(call: Call<List<TaskModel>>, t: Throwable) {
                listener.onFailure("Error: ${t.message}")
            }
        })
    }

    fun getDueInSevenDaysTasks(listener: APIListener<List<TaskModel>>) {
        taskService.getDueInSevenDaysTasks().enqueue(object : Callback<List<TaskModel>> {
            override fun onResponse(
                call: Call<List<TaskModel>>,
                response: Response<List<TaskModel>>
            ) {
                if(response.code() == Constants.HTTPStatusCode.OK) {
                    response.body()?.let { listener.onSuccess(it) }
                } else {
                    val errorBody = failResponse(response.errorBody()!!.string())
                    Log.d("TASKS_FETCHING", errorBody)
                    listener.onFailure(errorBody)
                }
            }

            override fun onFailure(call: Call<List<TaskModel>>, t: Throwable) {
                listener.onFailure("Error: ${t.message}")
            }
        })
    }

    fun overdueTasks(listener: APIListener<List<TaskModel>>) {
        taskService.getOverdueTasks().enqueue(object : Callback<List<TaskModel>> {
            override fun onResponse(
                call: Call<List<TaskModel>>,
                response: Response<List<TaskModel>>
            ) {
                if(response.code() == Constants.HTTPStatusCode.OK) {
                    response.body()?.let { listener.onSuccess(it) }
                } else {
                    val errorBody = failResponse(response.errorBody()!!.string())
                    Log.d("TASKS_FETCHING", errorBody)
                    listener.onFailure(errorBody)
                }
            }

            override fun onFailure(call: Call<List<TaskModel>>, t: Throwable) {
                listener.onFailure("Error: ${t.message}")
            }
        })
    }
}