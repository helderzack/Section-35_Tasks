package com.helder.section35_tasks.service.repository

import android.content.Context
import com.helder.section35_tasks.data.model.TaskModel
import com.helder.section35_tasks.service.listener.APIListener
import com.helder.section35_tasks.service.remote.RetrofitClient
import com.helder.section35_tasks.service.remote.TaskService

class TaskRepository(context: Context) : BaseRepository(context) {

    private val taskService = RetrofitClient.getService(TaskService::class.java)

    fun getAllTasks(listener: APIListener<List<TaskModel>>) {
        executeCall(taskService.getAllTasks(), listener)
    }

    fun getDueInSevenDaysTasks(listener: APIListener<List<TaskModel>>) {
        executeCall(taskService.getDueInSevenDaysTasks(), listener)
    }

    fun overdueTasks(listener: APIListener<List<TaskModel>>) {
        executeCall(taskService.getOverdueTasks(), listener)
    }

    fun getTask(id: Int, listener: APIListener<TaskModel>) {
        executeCall(taskService.getTask(id), listener)
    }

    fun createTask(task: TaskModel, listener: APIListener<Boolean>) {
        executeCall( taskService.create(
            task.priorityId,
            task.description,
            task.dueDate,
            task.complete
        ), listener)
    }

    fun updateTask(task: TaskModel, listener: APIListener<Boolean>) {
        executeCall(taskService.update(
            task.id,
            task.priorityId,
            task.description,
            task.dueDate,
            task.complete
        ), listener)
    }

    fun deleteTask(id: Int, listener: APIListener<Boolean>) {
        executeCall(taskService.remove(id), listener)
    }

    fun markComplete(id: Int, listener: APIListener<Boolean>) {
        executeCall(taskService.markComplete(id), listener)
    }

    fun markIncomplete(id: Int, listener: APIListener<Boolean>) {
        executeCall(taskService.markIncomplete(id), listener)
    }

}