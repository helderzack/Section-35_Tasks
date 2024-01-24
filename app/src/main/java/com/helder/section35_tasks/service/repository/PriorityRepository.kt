package com.helder.section35_tasks.service.repository

import android.content.Context
import com.helder.section35_tasks.data.model.PriorityModel
import com.helder.section35_tasks.service.listener.APIListener
import com.helder.section35_tasks.service.local.TaskDatabase
import com.helder.section35_tasks.service.remote.PriorityService
import com.helder.section35_tasks.service.remote.RetrofitClient

class PriorityRepository(val context: Context) : BaseRepository() {

    private val priorityService = RetrofitClient.getService(PriorityService::class.java)
    private val database = TaskDatabase.getDatabase(context).priorityDao()

    fun getPriorities(listener: APIListener<List<PriorityModel>>) {
        executeCall(priorityService.getPriorities(), listener)
    }

    fun savePriorities(priorities: List<PriorityModel>) {
        database.clear()
        database.save(priorities)
    }

}