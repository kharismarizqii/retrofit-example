package com.kharismarizqii.retrofitexercise.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.kharismarizqii.retrofitexercise.R
import com.kharismarizqii.retrofitexercise.api.RetrofitClient
import com.kharismarizqii.retrofitexercise.models.DefaultResponse
import com.kharismarizqii.retrofitexercise.storage.SharedPrefManager
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textViewLogin.setOnClickListener {
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
        }

        buttonSignUp.setOnClickListener {
            val email = editTextEmail.text.toString().trim()
            val password = editTextPassword.text.toString().trim()
            val name = editTextName.text.toString().trim()
            val school = editTextSchool.text.toString().trim()

            if (email.isEmpty()){
                editTextEmail.error = "Email Required"
                editTextEmail.requestFocus()
                return@setOnClickListener
            }
            if (password.isEmpty()){
                editTextPassword.error = "Password Required"
                editTextPassword.requestFocus()
                return@setOnClickListener
            }
            if (name.isEmpty()){
                editTextName.error = "Name Required"
                editTextName.requestFocus()
                return@setOnClickListener
            }
            if (school.isEmpty()){
                editTextSchool.error = "School Required"
                editTextSchool.requestFocus()
                return@setOnClickListener
            }

            RetrofitClient.instance.createUser(email,name,password,school)
                .enqueue(object : Callback<DefaultResponse>{
                    override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
                        Log.e("GAGAL", "GAGAL")
                    }

                    override fun onResponse(
                        call: Call<DefaultResponse>,
                        response: Response<DefaultResponse>
                    ) {
                        Toast.makeText(applicationContext, response.body()?.message, Toast.LENGTH_SHORT).show()
                        Log.e("SUCCESS", "GAGAL")
                    }

                })
        }
    }

    override fun onStart() {
        super.onStart()
        if (SharedPrefManager.getInstance(this).isLoggedIn){
            val intent = Intent(applicationContext, ProfileActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)
        }
    }
}
