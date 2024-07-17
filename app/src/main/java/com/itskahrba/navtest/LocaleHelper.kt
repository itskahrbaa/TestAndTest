package com.itskahrba.navtest

import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import android.content.res.Resources
import android.os.LocaleList
import java.util.Locale

object LocaleHelper {

    fun setLocale(context: Context, languageCode: String): ContextWrapper {

        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val configuration = context.resources.configuration.apply {
            setLocale(locale)
            val localeList = LocaleList(locale)
            LocaleList.setDefault(localeList)
            setLocales(localeList)
        }

        return ContextWrapper(context.createConfigurationContext(configuration))
    }
}

//    private fun setLocale(lang: String?) {
//        val myLocale: Locale = Locale(lang)
//        val res: Resources = this.resources
//        val dm = res.displayMetrics
//        val conf: Configuration = res.configuration
//        conf.locale = myLocale
//        res.updateConfiguration(conf, dm)
//        Locale.setDefault(myLocale)
//        onConfigurationChanged(conf)
//    }
