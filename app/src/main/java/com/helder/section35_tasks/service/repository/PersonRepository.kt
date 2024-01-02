package com.helder.section35_tasks.service.repository

import com.google.gson.Gson
import com.helder.section35_tasks.data.model.PersonModel
import com.helder.section35_tasks.service.constant.Constants
import com.helder.section35_tasks.service.listener.APIListener
import com.helder.section35_tasks.service.remote.PersonService
import com.helder.section35_tasks.service.remote.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PersonRepository: BaseRepository() {
    private val personService = RetrofitClient.getService(PersonService::class.java)

    fun doLogin(email: String, password: String, listener: APIListener<PersonModel>) {
        personService.login(email, password).enqueue(object : Callback<PersonModel> {
            override fun onResponse(call: Call<PersonModel>, response: Response<PersonModel>) {
                if(response.code() == 200) {
                    response.body()?.let { listener.onSuccess(it) }
                } else {
                    val errorBody = failResponse(response.errorBody()!!.string())
                    listener.onFailure(errorBody)
                }
            }

            override fun onFailure(call: Call<PersonModel>, t: Throwable) {
                listener.onFailure("Error: ${t.message.toString()}")
            }
        })
    }

    fun registerUser(name: String, email: String, password: String, listener: APIListener<PersonModel>) {
        personService.create(name, email, password).enqueue(object : Callback<PersonModel> {
            override fun onResponse(call: Call<PersonModel>, response: Response<PersonModel>) {
                if(response.code() == Constants.HTTPStatusCode.OK) {
                    response.body()?.let { listener.onSuccess(it) }
                } else {
                    val errorBody = failResponse(response.errorBody()!!.string())
                    listener.onFailure(errorBody)
                }
            }

            override fun onFailure(call: Call<PersonModel>, t: Throwable) {
                listener.onFailure("Error: ${t.message.toString()}")
            }
        })
    }
}