package com.itskahrba.navtest

import android.app.Application
import android.content.Context
import com.itskahrba.navtest.datastore.LanguageDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.util.Locale


class NavTest: Application()