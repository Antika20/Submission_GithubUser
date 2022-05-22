package com.example.githubuser.room


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.githubuser.helper.Fav


@Entity(tableName = "favorite")
 data class FavoriteEntity(

    @PrimaryKey
    @ColumnInfo(name = "username")
    val username: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "img_avatar")
    val img: String,
)

fun FavoriteEntity.myFavorite() = Fav(this.username, this.name, this.img)

fun List<FavoriteEntity>.toListFavorites(): MutableList<Fav> {
   val listFavorites = mutableListOf<Fav>()
   this.forEach { listFavorites.add(it.myFavorite()) }
   return listFavorites
}

