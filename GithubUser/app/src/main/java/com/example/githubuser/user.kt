package com.example.githubuser

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class user(
    var username: String,
    var name: String,
    var avatar: String,
    var company: String,
    var location: String,
    var repository: Int,
    var followers: Int,
    var following: Int,
): Parcelable

fun List<UserResponse>.toListUser(): MutableList<user> {
    val listUser = mutableListOf<user>()
    this?.forEach {
        listUser.add(
            user(
                username=it.login?:"",
                name=it.name?:"",
                avatar=it.avatarUrl?:"",
                company =it.company?:"",
                location = it.location?:"",
                repository =it.publicRepos?:0,
                followers = it.followers?:0,
                following = it.following?:0,
            )
        )
    }
    return listUser
}

fun UserResponse.toUser(): user {
    return user(
        username=this.login?:"",
        name=this.name?:"",
        avatar=this.avatarUrl?:"",
        company =this.company?:"",
        location = this.location?:"",
        repository =this.publicRepos?:0,
        followers = this.followers?:0,
        following = this.following?:0,
    )
}


