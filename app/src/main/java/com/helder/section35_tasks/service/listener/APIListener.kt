package com.helder.section35_tasks.service.listener

interface APIListener<T> {

    fun onSuccess(result: T)

    fun onFailure(message: String)
}