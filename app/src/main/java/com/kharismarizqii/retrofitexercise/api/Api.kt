package com.kharismarizqii.retrofitexercise.api

import com.kharismarizqii.retrofitexercise.models.DefaultResponse
import com.kharismarizqii.retrofitexercise.models.LoginResponse
import com.kharismarizqii.retrofitexercise.models.UsersResponse
import retrofit2.Call
import retrofit2.http.*

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

    @FormUrlEncoded
    @PUT("updateuser/{id}")
    fun updateUser(
        @Path("id") id: Int?,
        @Field("email") email:String?,
        @Field("name") name: String?,
        @Field("school") school: String?
    ): Call<LoginResponse>

    @FormUrlEncoded
    @PUT("updatepassword")
    fun updatePassword(
        @Field("currentpassword") currentpassword: String,
        @Field("newpassword") newpassword: String,
        @Field("email") email: String
    ): Call<DefaultResponse>

    @DELETE("deleteuser/{id}")
    fun deleteUser(
        @Path("id") id: Int?
    ): Call<DefaultResponse>
}