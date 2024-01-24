package com.helder.section35_tasks.service.repository

import android.content.Context
import com.helder.section35_tasks.data.model.PersonModel
import com.helder.section35_tasks.service.listener.APIListener
import com.helder.section35_tasks.service.remote.PersonService
import com.helder.section35_tasks.service.remote.RetrofitClient

class PersonRepository(context: Context) : BaseRepository(context) {

    private val personService = RetrofitClient.getService(PersonService::class.java)

    fun doLogin(email: String, password: String, listener: APIListener<PersonModel>) {
        executeCall(personService.login(email, password), listener)
    }

    fun registerUser(name: String, email: String, password: String, listener: APIListener<PersonModel>) {
        executeCall(personService.create(name, email, password), listener)
    }

}