package com.helder.section35_tasks.service.repository

import android.content.Context
import com.helder.section35_tasks.data.model.PriorityModel
import com.helder.section35_tasks.service.listener.APIListener
import com.helder.section35_tasks.service.local.TaskDatabase
import com.helder.section35_tasks.service.remote.PriorityService
import com.helder.section35_tasks.service.remote.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PriorityRepository(val context: Context) : BaseRepository() {

    private val priorityService = RetrofitClient.getService(PriorityService::class.java)
    private val database = TaskDatabase.getDatabase(context).priorityDao()

    fun getPriorities(listener: APIListener<List<PriorityModel>>) {
        priorityService.getPriorities().enqueue(object : Callback<List<PriorityModel>> {
            override fun onResponse(
                call: Call<List<PriorityModel>>,
                response: Response<List<PriorityModel>>
            ) {
                handleResponse(response, listener)
            }

            override fun onFailure(call: Call<List<PriorityModel>>, t: Throwable) {
                listener.onFailure("Error: ${t.message}")
            }
        })

    }

    fun savePriorities(priorities: List<PriorityModel>) {
        database.clear()
        database.save(priorities)
    }
}