package com.kharismarizqii.retrofitexercise.models

data class LoginResponse(
    val error: Boolean,
    val message: String,
    val user: User
)