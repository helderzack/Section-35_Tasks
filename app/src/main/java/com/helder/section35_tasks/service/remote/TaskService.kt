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
import java.time.LocalDate

interface TaskService {

    @GET("Task")
    fun getAllTasks(): Call<List<TaskModel>>

    @GET("Task/Next7Days")
    fun getDueInSevenDaysTasks(): Call<List<TaskModel>>

    @GET("Task/Overdue")
    fun getOverdueTasks(): Call<List<TaskModel>>

    @GET("Task/{Id}")
    fun getTask(@Path(value = "Id", encoded = true) id: Int): Call<TaskModel>

    @POST("Task")
    @FormUrlEncoded
    fun create(
        @Field("PriorityId") priorityId: Int,
        @Field("Description") description: String,
        @Field("DueDate") dueDate: LocalDate,
        @Field("Complete") complete: Boolean
    ): Call<Boolean>

    @PUT("Task")
    @FormUrlEncoded
    fun update(
        @Field("Id") id: Int,
        @Field("PriorityId") priorityId: Int,
        @Field("Description") description: String,
        @Field("DueDate") dueDate: LocalDate,
        @Field("Complete") complete: Boolean
    ): Call<Boolean>

    @DELETE("Task")
    @FormUrlEncoded
    fun remove(@Field("Id") id: Int): Call<Boolean>

    @PUT("Task/Complete")
    @FormUrlEncoded
    fun markComplete(@Field("Id") id: Int): Call<Boolean>

    @PUT("Task/Undo")
    @FormUrlEncoded
    fun markIncomplete(@Field("Id") id: Int): Call<Boolean>

}