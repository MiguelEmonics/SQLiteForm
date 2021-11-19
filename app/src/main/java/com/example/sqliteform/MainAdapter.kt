package com.example.sqliteform

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sqliteform.databinding.AdapterMovieBinding
import com.example.sqliteform.databinding.ItemView1Binding

class MainAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var users = mutableListOf<User>()

    fun setUserList(users: MutableList<User>) {
        this.users = users.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        if (viewType == MainActivity.VIEW_TYPE_HEADER) {
            val binding = ItemView1Binding.inflate(inflater, parent, false)
            return SecondHeaderLayout(binding)
        } else {
            val binding = AdapterMovieBinding.inflate(inflater, parent, false)
            return MainViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val user = users[position]
        if (user.view_type == MainActivity.VIEW_TYPE_HEADER) {
            val header = holder as SecondHeaderLayout
            header.header.textView.text = user.username
        } else {
            val detail = holder as MainViewHolder
            //detail.binding.name.text = user.username
            detail.binding.email.text = user.email
            detail.binding.phone.text = user.phone
            detail.binding.adress.text = user.address
            detail.binding.city.text = user.city
            detail.binding.state.text = user.state
        }
    }

    override fun getItemViewType(position: Int): Int {
        return users[position].view_type
    }

    override fun getItemCount(): Int {
        return users.size
    }
}

class MainViewHolder(val binding: AdapterMovieBinding) : RecyclerView.ViewHolder(binding.root) {

}

class SecondHeaderLayout(val header: ItemView1Binding) : RecyclerView.ViewHolder(header.root) {

}