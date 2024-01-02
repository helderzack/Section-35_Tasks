package com.helder.section35_tasks.service.remote

import com.helder.section35_tasks.data.model.TaskModel
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TaskService {

    @GET("Task")
    fun getAllTasks(): Call<List<TaskModel>>

    @GET("Task/Next7Days")
    fun getDueInSevenDaysTasks(): Call<List<TaskModel>>

    @GET("Task/Overdue")
    fun getOverdueTasks(): Call<List<TaskModel>>

    @GET("Task/{id}")
    fun getTask(@Path("id") id: Int): Call<TaskModel>

    @POST("Task")
    @FormUrlEncoded
    fun save(
        @Field("priorityId") priorityId: Int,
        @Field("description") description: String,
        @Field("dueDate") dueDate: String,
        @Field("complete") complete: Boolean
    ): Call<Boolean>

    @PUT("Task")
    fun update(
        @Field("id") id: Int,
        @Field("priorityId") priorityId: Int,
        @Field("description") description: String,
        @Field("dueDate") dueDate: String,
        @Field("complete") complete: Boolean
    ): Call<Boolean>

    @DELETE("Task")
    fun remove(@Field("id") id: Int): Call<Boolean>

    @PUT("Task/Complete")
    fun markComplete(@Field("id") id: Int): Call<Boolean>

    @PUT("Task/Undo")
    fun markIncomplete(@Field("id") id: Int): Call<Boolean>

}