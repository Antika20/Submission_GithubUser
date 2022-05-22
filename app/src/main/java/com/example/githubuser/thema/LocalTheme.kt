package com.example.githubuser.thema

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object LocalTheme {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "themes")
    private val THEMES_KEY = booleanPreferencesKey("themes_key")

    fun getTheme(context: Context): Flow<Boolean> =
        context.dataStore.data.map { preferences -> preferences[THEMES_KEY] ?: false }

    suspend fun updateTheme(context: Context, isDarkMode: Boolean) {
        context.dataStore.edit { settings -> settings[THEMES_KEY] = isDarkMode }
    }
}