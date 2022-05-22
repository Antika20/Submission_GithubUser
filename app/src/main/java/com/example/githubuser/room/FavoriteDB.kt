package com.example.githubuser.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [FavoriteEntity::class], version = 2, exportSchema = false)
abstract class FavoriteDB : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao

    companion object {
        @Volatile
        private var instance: FavoriteDB? = null
        fun getInstance(context: Context): FavoriteDB =
            instance ?: synchronized(this) {
                 Room.databaseBuilder(
                    context.applicationContext,
                    FavoriteDB::class.java, "Fav.db"
                ).fallbackToDestructiveMigration().build()
            }
    }
}
