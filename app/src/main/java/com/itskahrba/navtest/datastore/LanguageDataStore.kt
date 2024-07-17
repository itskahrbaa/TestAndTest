package com.itskahrba.navtest.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Locale

class LanguageDataStore(context: Context) : LanguageDS {
    private val dataStore = context.dataStore
    override val language: Flow<String>
        get() = dataStore.data.map { preferences -> preferences[LANGUAGE_KEY] ?: Locale.getDefault().language }

    override suspend fun saveToDataStore(newLang: String) {
        dataStore.edit {
            it[LANGUAGE_KEY] = newLang
        }
    }

//    override suspend fun replaceBetweenEnAr() {
//        dataStore.edit { preferences ->
//            val currentLang = preferences[LANGUAGE_KEY]
//            val newLang = if (currentLang == "en") "ar" else "en"
//            preferences[LANGUAGE_KEY] = newLang
//        }
//    }

    companion object {
        private val LANGUAGE_KEY = stringPreferencesKey("language_key")
    }

}

interface LanguageDS {
    val language: Flow<String>
    suspend fun saveToDataStore(newLang: String)
//    suspend fun replaceBetweenEnAr()
}