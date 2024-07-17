package com.itskahrba.navtest

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.util.Log
import com.itskahrba.navtest.datastore.LanguageDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.util.Locale

class LocaleManger(
    private val context: Context
) : LanguageManager {


    override fun applyCurrentLanguage() {
        val currentLanguage = getPersistedLanguage()
        updateResources(currentLanguage)

    }

    override fun setAndApplyNewLanguage(language: String) {
        persistLanguage(language)
        updateResources(language)
    }

    override fun replaceBetweenArEn() {
        val currentLanguage = getPersistedLanguage()
        if (currentLanguage == "ar") {
            setAndApplyNewLanguage("en")
        } else {
            setAndApplyNewLanguage("ar")
        }
    }

    override fun getPersistedLanguage(): String {
        val languageDataStore = LanguageDataStore(context)
        return runBlocking {
            languageDataStore.language.first()
        }
    }

    override fun persistLanguage(language: String) {
        val languageDataStore = LanguageDataStore(context)
        runBlocking {
            languageDataStore.saveToDataStore(language)
        }
    }

    override fun updateResources(language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)

        val res = context.resources
        val config = Configuration(res.configuration)
        config.setLocale(locale)
        res.updateConfiguration(config, res.displayMetrics)

    }

}

interface LanguageManager {
    fun applyCurrentLanguage()
    fun setAndApplyNewLanguage(language: String)
    fun replaceBetweenArEn()
    fun getPersistedLanguage(): String
    fun persistLanguage(language: String)
    fun updateResources(language: String)
}