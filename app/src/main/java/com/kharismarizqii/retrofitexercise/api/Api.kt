package com.kharismarizqii.retrofitexercise.api

import com.kharismarizqii.retrofitexercise.models.DefaultResponse
import com.kharismarizqii.retrofitexercise.models.LoginResponse
import com.kharismarizqii.retrofitexercise.models.UsersResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface Api {

    @FormUrlEncoded
    @POST("createuser")
    fun createUser(
        @Field("email") email:String,
        @Field("name") name:String,
        @Field("password") password: String,
        @Field("school") school: String
    ): Call<DefaultResponse>

    @FormUrlEncoded
    @POST("userlogin")
    fun userLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @GET("allusers")
    fun getUser(): Call<UsersResponse>
}