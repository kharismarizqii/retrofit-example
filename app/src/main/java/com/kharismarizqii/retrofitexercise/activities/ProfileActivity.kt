package com.kharismarizqii.retrofitexercise.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kharismarizqii.retrofitexercise.R
import com.kharismarizqii.retrofitexercise.storage.SharedPrefManager
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val user = SharedPrefManager.getInstance(this).user

        tvWMsg.text = "Welcome Back ${user.name}"
    }

    override fun onStart() {
        super.onStart()
        if (!SharedPrefManager.getInstance(this).isLoggedIn){
            val intent = Intent(applicationContext, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)
        }
    }
}
