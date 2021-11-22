package com.example.sqliteform

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sqliteform.databinding.AdapterMovieBinding
import com.example.sqliteform.databinding.ItemView1Binding
import com.example.sqliteform.databinding.ItemView2Binding

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
        } else if (viewType == MainActivity.VIEW_TYPE_FOOTER) {
            val binding = ItemView2Binding.inflate(inflater, parent, false)
            return FooterHeaderLayout(binding)
        }else {
            val binding = AdapterMovieBinding.inflate(inflater, parent, false)
            return MainViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val user = users[position]
        if (user.view_type == MainActivity.VIEW_TYPE_HEADER) {
            val header = holder as SecondHeaderLayout
            header.header.textView.text = user.username
        } else if (user.view_type == MainActivity.VIEW_TYPE_FOOTER) {
            val footer = holder as FooterHeaderLayout
            footer.footer.phone.text = user.phone
            footer.footer.email.text = user.email
        } else {
            val detail = holder as MainViewHolder
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

class FooterHeaderLayout(val footer: ItemView2Binding) : RecyclerView.ViewHolder(footer.root) {

}