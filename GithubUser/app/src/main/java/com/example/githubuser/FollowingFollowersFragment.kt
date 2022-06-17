package com.example.githubuser

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch


class FollowingFollowersFragment : Fragment(), ListUserAdapter.OnItemClickCallback {

    private lateinit var rvFollowers: RecyclerView
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_following_followers2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvFollowers = view.findViewById(R.id.rv_Followers)
        progressBar = view.findViewById(R.id.progressBarFollowers)
        progressBar.isVisible = true
        arguments?.apply {
            getInt("position").let { position ->
                println("position = $position")
                getString("username")?.let { username ->
                    println("username = $username")
                    if (position == 0) getFollowers(username) else getFollowing(username)
                }
            }
        }
    }

    private fun getFollowing(username: String) = lifecycleScope.launch {
        ApiConfig.getApiService().getFollowing(username).let {
            populateData(it.toListUser())
            progressBar.isVisible = false
        }
    }


    private fun getFollowers(username: String) = lifecycleScope.launch {
        ApiConfig.getApiService().getFollowers(username).let {
            populateData(it.toListUser())
            progressBar.isVisible = false
        }
    }

    private fun populateData(listUser: List<user>) {
        val listUserAdapter = ListUserAdapter(listUser)
        listUserAdapter.setOnItemClickCallback (this)
       rvFollowers.apply {
           itemAnimator = DefaultItemAnimator()
           adapter = listUserAdapter

       }
    }

    override fun onItemClicked(data: user) {
        startActivity(
            Intent(requireContext(),UserDetail::class.java).apply {
                putExtra(UserDetail.EXTRA_user,data)
            }
        )
    }
}

