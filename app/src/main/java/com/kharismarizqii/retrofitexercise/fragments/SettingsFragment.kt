package com.kharismarizqii.retrofitexercise.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.kharismarizqii.retrofitexercise.R
import com.kharismarizqii.retrofitexercise.activities.LoginActivity
import com.kharismarizqii.retrofitexercise.api.RetrofitClient
import com.kharismarizqii.retrofitexercise.models.DefaultResponse
import com.kharismarizqii.retrofitexercise.models.LoginResponse
import com.kharismarizqii.retrofitexercise.storage.SharedPrefManager
import kotlinx.android.synthetic.main.fragment_settings.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_save.setOnClickListener {
            updateProfile()
        }
        btn_logout.setOnClickListener {
            logout()
        }
        btn_change_pass.setOnClickListener {
            updatePassword()
        }
        btn_delete_account.setOnClickListener {
            deleteUser()
        }
    }

    private fun deleteUser() {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Are You Sure?")
        builder.setMessage("this action is irreversible...")
        builder.setPositiveButton("Yes", object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                val user = activity?.let { SharedPrefManager.getInstance(it).user }
                val call = RetrofitClient.instance.deleteUser(user?.id)
                call.enqueue(object : Callback<DefaultResponse> {
                    override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {

                    }

                    override fun onResponse(
                        call: Call<DefaultResponse>,
                        response: Response<DefaultResponse>
                    ) {
                        if (!response.body()?.error!!) {
                            activity?.let { SharedPrefManager.getInstance(it).clear() }
                            val intent = Intent(activity, LoginActivity::class.java)
                            intent.flags =
                                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                        }
                    }

                })
            }
        })
        builder.setNegativeButton("No", object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {

            }
        })
        val ad = builder.create()
        ad.show()
    }

    private fun logout() {
        activity?.let { SharedPrefManager.getInstance(it).clear() }
        val intent = Intent(activity, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun updateProfile() {
        val email = et_email.text.toString().trim()
        val name = et_name.text.toString().trim()
        val school = et_school.text.toString().trim()

        if (email.isEmpty()) {
            et_email.error = "Email Required"
            et_email.requestFocus()
            return
        }
        if (name.isEmpty()) {
            et_name.error = "Name Required"
            et_name.requestFocus()
            return
        }
        if (school.isEmpty()) {
            et_school.error = "School Required"
            et_school.requestFocus()
            return
        }

        val user = activity?.let { SharedPrefManager.getInstance(it).user }


        val call = RetrofitClient.instance.updateUser(
            user?.id,
            user?.email,
            user?.name,
            user?.school
        )

        call.enqueue(object : Callback<LoginResponse> {
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {

            }

            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                Toast.makeText(activity, response.body()?.message, Toast.LENGTH_SHORT).show()

                if (!response.body()?.error!!) {
                    activity?.let {
                        SharedPrefManager.getInstance(it).saveUser(
                            response.body()!!.user
                        )
                    }
                }
                Toast.makeText(activity, response.body()?.message, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun updatePassword() {
        val currentpassword = et_current.text.toString().trim()
        val newpassword = et_new_pass.text.toString().trim()

        if (currentpassword.isEmpty()) {
            et_current.error = "Password required"
            et_current.requestFocus()
            return
        }

        if (newpassword.isEmpty()) {
            et_new_pass.error = "Password REquired"
            et_new_pass.requestFocus()
            return
        }

        val user = activity?.let { SharedPrefManager.getInstance(it).user }
        val call = user?.email?.let {
            RetrofitClient.instance.updatePassword(
                currentpassword,
                newpassword,
                it
            )
        }

        call?.enqueue(object : Callback<DefaultResponse> {
            override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<DefaultResponse>,
                response: Response<DefaultResponse>
            ) {
                Toast.makeText(activity, response.body()?.message, Toast.LENGTH_SHORT).show()
            }

        })
    }
}
