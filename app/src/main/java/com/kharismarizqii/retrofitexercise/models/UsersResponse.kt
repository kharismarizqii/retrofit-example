package com.kharismarizqii.retrofitexercise.models

data class UsersResponse(
    val error: Boolean,
    val users: ArrayList<User>
)