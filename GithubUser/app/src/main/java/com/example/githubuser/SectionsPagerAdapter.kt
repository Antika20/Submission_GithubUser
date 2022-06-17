package com.example.githubuser

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter(activity: AppCompatActivity,private val username :String) : FragmentStateAdapter(activity) {

    private val FollowFragment = listOf(
        FollowingFollowersFragment(),
        FollowingFollowersFragment()
    )

    override fun getItemCount(): Int =FollowFragment.size


    override fun createFragment(position: Int): Fragment  = FollowFragment[position].apply {
        arguments = Bundle().apply {
            putString("username", username)
            putInt("position", position)
        }

    }
}
