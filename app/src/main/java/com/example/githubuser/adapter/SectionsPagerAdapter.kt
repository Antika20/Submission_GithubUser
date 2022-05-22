package com.example.githubuser.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.githubuser.fragment.FollowingFollowersFragment

class SectionsPagerAdapter(activity: AppCompatActivity,private val username :String) : FragmentStateAdapter(activity) {

    private val followFragment = listOf(
        FollowingFollowersFragment(),
        FollowingFollowersFragment()
    )

    override fun getItemCount(): Int =followFragment.size


    override fun createFragment(position: Int): Fragment  = followFragment[position].apply {
        arguments = Bundle().apply {
            putString("username", username)
            putInt("position", position)
        }
    }
}
