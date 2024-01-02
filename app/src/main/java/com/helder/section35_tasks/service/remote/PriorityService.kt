package com.helder.section35_tasks.service.remote

import com.helder.section35_tasks.data.model.PriorityModel
import retrofit2.Call
import retrofit2.http.GET

interface PriorityService {

    @GET("Priority")
    fun getPriorities(): Call<List<PriorityModel>>
}