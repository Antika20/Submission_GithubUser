package com.example.githubuser

import android.os.Parcelable
import com.example.githubuser.helper.Fav
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var username: String,
    var name: String,
    var avatar: String,
    var company: String,
    var location: String,
    var repository: Int,
    var followers: Int,
    var following: Int,
): Parcelable

fun List<UserResponse>.toListUser(): MutableList<User> {
    val listUser = mutableListOf<User>()
    this.forEach {
        listUser.add(
            User(
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

fun UserResponse.toUser(): User {
    return User(
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
//Translate
fun User.toFav() = Fav(
    username = this.username, name = this.name, imgAvatar = this.avatar
)



