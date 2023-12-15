package com.helder.section35_tasks.service.remote

import com.helder.section35_tasks.data.model.PersonModel
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface PersonService {

    @POST("Authentication/Create")
    @FormUrlEncoded
    fun create(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
    ): Call<PersonModel>

    @POST("Authentication/Login")
    @FormUrlEncoded
    fun login(
        @Field("email") email: String,
        @Field("password") password: String,
    ): Call<PersonModel>
}