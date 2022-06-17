package com.example.githubuser

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.card.MaterialCardView


class ListUserAdapter(private val listUser: List<user>) : RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_avatar, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = listUser[position]
        Glide.with(holder.cardProfile.context).load(user.avatar).circleCrop().into(holder.imgPhoto)
        holder.tvUsername.text = user.username
        holder.cardProfile.setOnClickListener{
            onItemClickCallback.onItemClicked(user)
        }


    }

    override fun getItemCount(): Int = listUser.size

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)  {
        var imgPhoto= itemView.findViewById <ImageView> (R.id.img_Photo)
        var tvUsername = itemView.findViewById <TextView> (R.id.tv_item_Username)
        val cardProfile = itemView.findViewById <MaterialCardView> (R.id.card_view_profile)

    }
    interface OnItemClickCallback {
        fun onItemClicked(data: user)


    }
}




