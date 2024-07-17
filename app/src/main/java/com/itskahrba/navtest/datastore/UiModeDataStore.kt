package com.itskahrba.navtest.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UiModeDataStore(context: Context) : UIMode {
    private val dataStore = context.dataStore
    override val uiMode: Flow<Boolean>
        get() = dataStore.data.map { preferences -> preferences[UI_MODE_KEY] ?: false }

    override suspend fun saveToDataStore(isNightMode: Boolean) {
        dataStore.edit { preferences -> preferences[UI_MODE_KEY] = isNightMode }
    }
    override suspend fun changeUIMode() {
        dataStore.edit { preferences -> preferences[UI_MODE_KEY] = !preferences[UI_MODE_KEY]!! }
    }

    companion object {
        private val UI_MODE_KEY = booleanPreferencesKey("ui_mode")
    }

}

interface UIMode {
    val uiMode: Flow<Boolean>
    suspend fun saveToDataStore(isNightMode: Boolean)
    suspend fun changeUIMode()

}