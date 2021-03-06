package com.example.githubuser.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuser.User
import com.example.githubuser.databinding.ItemAvatarBinding



class ListUserAdapter(private val listUser: List<User>) : RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback
    private lateinit var binding: ItemAvatarBinding

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        binding = ItemAvatarBinding.inflate(
            LayoutInflater.from(parent.context),parent, false
        )
        return ListViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val User = listUser[position]
        with(binding){
            Glide.with(imgPhoto.context).load(User.avatar).circleCrop().into(imgPhoto)
            tvItemUsername.text = User.username
            cardViewProfile.setOnClickListener{
                onItemClickCallback.onItemClicked(User)
            }
        }
    }

    override fun getItemCount(): Int = listUser.size

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    interface OnItemClickCallback {
        fun onItemClicked(data: User)


    }
}




