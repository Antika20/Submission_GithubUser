package com.example.githubuser.room


import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface FavoriteDao {
    @Query("Select * From favorite")
    fun getAllFavorite():Flow<List<FavoriteEntity>>

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFavorite (favoriteEntity: FavoriteEntity)

    @Query("SELECT EXISTS(SELECT * FROM favorite WHERE username = :username )")
    fun getCheckFavorite(username: String):Flow<Boolean>
    @Delete
    suspend fun delete(favoriteEntity: FavoriteEntity)
}

