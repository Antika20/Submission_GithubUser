package com.example.githubuser.helper

import com.example.githubuser.User
import com.example.githubuser.room.FavoriteEntity

data class Fav(
    val username: String,
    val name: String,
    val imgAvatar: String,
)

fun Fav.toFavoritesEntity() = FavoriteEntity(this.username, this.name, this.imgAvatar)
// Translate
fun  Fav.toUser() =  User(
    username = this.username, avatar = this.imgAvatar, name = "",
    company = "", location = "", repository = 0, followers = 0, following = 0
)

fun List<Fav>.toListUser(): MutableList<User> {
    val listUser = mutableListOf<User>()
    this.forEach { listUser.add(it.toUser()) }
    return listUser
}