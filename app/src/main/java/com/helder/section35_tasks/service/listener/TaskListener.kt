package com.helder.section35_tasks.service.listener

interface TaskListener<T> {

    fun onSuccess(result: List<T>)

    fun onFailure(message: String)
}