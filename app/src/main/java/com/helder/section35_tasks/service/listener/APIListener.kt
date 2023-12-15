package com.helder.section35_tasks.service.listener

import com.helder.section35_tasks.data.model.PersonModel

interface APIListener<T> {

    fun onSuccess(result: T)

    fun onFailure(message: String)
}