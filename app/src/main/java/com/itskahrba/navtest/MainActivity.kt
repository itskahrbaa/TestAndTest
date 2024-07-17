package com.itskahrba.navtest

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import androidx.navigation.toRoute
import com.itskahrba.navtest.datastore.UiModeDataStore
import com.itskahrba.navtest.screens.HomeScreen
import com.itskahrba.navtest.ui.theme.NavTestTheme
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable


class MainActivity : ComponentActivity() {
    private lateinit var uiModeDataStore: UiModeDataStore

    override fun attachBaseContext(newBase: Context) {
        LocaleManger(newBase).applyCurrentLanguage()
        super.attachBaseContext(newBase)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        uiModeDataStore = UiModeDataStore(this)

        observeUITheme()

        setContent {
            NavTestMain()
        }
    }


    @Composable
    fun NavTestMain() {

        // check UI mode
        val darkMode by uiModeDataStore.uiMode.collectAsState(initial = isSystemInDarkTheme())
//        val language by languageDataStore.language.collectAsState(initial = "ar")

        // set UI mode accordingly
        val toggleTheme: () -> Unit = {
            lifecycleScope.launch {
                uiModeDataStore.saveToDataStore(!darkMode)
            }
        }

        val toggleLanguage: () -> Unit = {
            LocaleManger(this).replaceBetweenArEn()
            recreate()
        }

        NavTestTheme(darkTheme = darkMode) {
            NavGraph(
                toggleTheme = toggleTheme,
                toggleLanguage = toggleLanguage
            )
        }

    }

    private fun observeUITheme() {
        lifecycleScope.launch {
            uiModeDataStore.uiMode.collect {
                val mode = when (it) {
                    true -> AppCompatDelegate.MODE_NIGHT_YES
                    false -> AppCompatDelegate.MODE_NIGHT_NO
                }
                AppCompatDelegate.setDefaultNightMode(mode)
            }
            cancel()
        }
    }
}


@Composable
fun NavGraph(toggleTheme: () -> Unit, toggleLanguage: () -> Unit) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = HomeScreen) {

        composable<HomeScreen> {

            HomeScreen(
                toggleTheme = toggleTheme,
                onArticleSelected = { articleId ->
                    navController.navigate(
                        DetailScreen(
                            articleId = articleId.toString()
                        )
                    )
                }
            )
        }

        composable<DetailScreen>(
            deepLinks = listOf(navDeepLink {
                uriPattern = "https://kolbook.xyz/series/{articleId}/"
            })
        ) { backStackEntry ->
            val articleId = backStackEntry.toRoute<DetailScreen>().articleId
            DetailsScreen(
                toggleLanguage = toggleLanguage, s = articleId
            )
        }
    }
}

@Serializable
object HomeScreen

@Serializable
data class DetailScreen(
    val articleId: String
)

@Composable
fun DetailsScreen(
    s: String,
    toggleLanguage: () -> Unit
) {
    Surface {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Button(onClick = { toggleLanguage() }) {
                Text(text = stringResource(id = R.string.change_language))
            }
        }
    }

}
