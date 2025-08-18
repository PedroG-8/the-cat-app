package com.myapps.thecatapp.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "cat_preferences")

class CatPreferences(private val dataStore: DataStore<Preferences>) {

    companion object {
        private val KEY_LAST_PAGE = intPreferencesKey("last_page")
    }

    suspend fun saveLastPage(page: Int) {
        dataStore.edit { prefs ->
            prefs[KEY_LAST_PAGE] = page
        }
    }

    val lastPageFlow: Flow<Int> = dataStore.data
        .map { prefs -> prefs[KEY_LAST_PAGE] ?: 0 }
}