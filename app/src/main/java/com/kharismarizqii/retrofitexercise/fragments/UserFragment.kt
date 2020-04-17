package com.kharismarizqii.retrofitexercise.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.kharismarizqii.retrofitexercise.R
import com.kharismarizqii.retrofitexercise.adapters.UserAdapter
import com.kharismarizqii.retrofitexercise.api.RetrofitClient
import com.kharismarizqii.retrofitexercise.models.User
import com.kharismarizqii.retrofitexercise.models.UsersResponse
import kotlinx.android.synthetic.main.fragment_user.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class UserFragment : Fragment() {

    private lateinit var list: ArrayList<User>
    private lateinit var adapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvUser.setHasFixedSize(true)
        rvUser.layoutManager = LinearLayoutManager(activity)
        val call = RetrofitClient.instance.getUser()
        call.enqueue(object : Callback<UsersResponse>{
            override fun onFailure(call: Call<UsersResponse>, t: Throwable) {

            }

            override fun onResponse(call: Call<UsersResponse>, response: Response<UsersResponse>) {
                list = response.body()?.users!!
                adapter = UserAdapter(list)
                rvUser.adapter = adapter
            }
        })
    }

}
