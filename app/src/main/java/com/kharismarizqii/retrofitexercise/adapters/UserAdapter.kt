package com.kharismarizqii.retrofitexercise.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kharismarizqii.retrofitexercise.R
import com.kharismarizqii.retrofitexercise.models.User
import kotlinx.android.synthetic.main.item_user.view.*

class UserAdapter(private val list: ArrayList<User>) : RecyclerView.Adapter<UserAdapter.UsersViewHolder>(){

    inner class UsersViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(user: User){
            with(itemView){
                tvName.text = user.name
                tvEmail.text = user.email
                tvSchool.text = user.school
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UsersViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        holder.bind(list[position])
    }


}