package com.example.githubuser.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DefaultItemAnimator
import com.example.githubuser.*
import com.example.githubuser.adapter.ListUserAdapter
import com.example.githubuser.databinding.FragmentFollowingFollowers2Binding
import com.example.githubuser.ui.detail.MainViewModel
import com.example.githubuser.helper.ViewModelFactory
import com.example.githubuser.ui.main.UserDetail
import kotlinx.coroutines.launch



class FollowingFollowersFragment : Fragment(), ListUserAdapter.OnItemClickCallback {

    private lateinit var binding: FragmentFollowingFollowers2Binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = FragmentFollowingFollowers2Binding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = ViewModelFactory.getInstance(requireContext())
        val mainViewModel: MainViewModel by viewModels{
            factory
        }


        binding.progressBarFollowers.isVisible = true
        arguments?.apply {
            getInt("position").let { position ->
                println("position = $position")
                getString("username")?.let { username ->
                    println("username = $username")
                    if (position == 0) getFollowers(username,mainViewModel)
                    else getFollowing(username,mainViewModel)
                }
            }
        }

        mainViewModel.user.observe(viewLifecycleOwner ) {
            showPopulateData(it)
            binding.progressBarFollowers.isVisible = false
        }
    }


    private fun getFollowing(username: String, mainViewModel: MainViewModel) = lifecycleScope.launch {
        mainViewModel.getFollowing(username)
    }


    private fun getFollowers(username: String,mainViewModel: MainViewModel) = lifecycleScope.launch {
        mainViewModel.getFollowers(username)
    }

    private fun showPopulateData(listUser: List<User>) {
        val listUserAdapter = ListUserAdapter(listUser)
        listUserAdapter.setOnItemClickCallback (this)
        binding.rvFollowers.apply {
            itemAnimator = DefaultItemAnimator()
            adapter = listUserAdapter

        }
    }

    override fun onItemClicked(data: User) {
        startActivity(
            Intent(requireContext(), UserDetail::class.java).apply {
                putExtra(UserDetail.EXTRA_user,data)
            }
        )
    }
}