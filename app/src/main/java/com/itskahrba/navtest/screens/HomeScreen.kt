package com.itskahrba.navtest.screens

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.itskahrba.navtest.R
import com.itskahrba.navtest.datastore.LanguageDataStore
import kotlinx.coroutines.launch
import java.util.Locale


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onArticleSelected: (Int) -> Unit,
    toggleTheme: () -> Unit,
) {
    val context = LocalContext.current
    val language by LanguageDataStore(context).language.collectAsState(initial = "ar")
    val scope = rememberCoroutineScope()

    Surface {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(
                modifier = modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(stringResource(R.string.home_screen))
                Divider(
                    modifier = Modifier.padding(vertical = 20.dp)
                )
                Button(onClick = { toggleTheme() }) {
                    Text(text = stringResource(R.string.update_ui))
                }
                Divider(
                    modifier = Modifier.padding(vertical = 20.dp)
                )

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.except)+": $language"
                )
                Spacer(modifier = Modifier.height(5.dp))

                Divider(
                    modifier = Modifier.padding(vertical = 20.dp)
                )
                Button(onClick = { onArticleSelected(1) }) {
                    Text(text = "onArticleSelected")
                }
            }
        }
    }
}

//
//@Composable
//fun DiscordButton() {
//    val context = LocalContext.current
//
//    Button(onClick = {
//        context.startActivity(getOpenDiscordIntent(context))
//    }) {
//        Text(text = "اذهب إلى روم الديسكورد")
//    }
//}
//
//fun getOpenDiscordIntent(context: Context): Intent {
//    val invite = "https://discord.gg/acFMsvHqyN"
//    val guildId = "646751050883399703"
//    val channelId = "934416493129637908"
//    val discordUri = "https://discord.com/channels/$guildId/$channelId"
//    val packageName = "com.discord"
//
//    try {
//        context.packageManager.getPackageInfo(packageName, 0)
//        return Intent(Intent.ACTION_VIEW, Uri.parse(discordUri))
//    } catch (e: Exception) {
//        return Intent(Intent.ACTION_VIEW, Uri.parse(invite))
//    }
//}
//
//@Composable
//fun ShareButton() {
//    val context = LocalContext.current
//    val targetPackage = "com.discord" // ضع هنا اسم الحزمة للتطبيق المخصص
//    val shareText = "هذا نص المشاركة"
//
//    Button(onClick = {
//        if (isAppInstalled(context, targetPackage)) {
//            val shareIntent = Intent(Intent.ACTION_SEND).apply {
//                type = "text/plain"
//                putExtra(Intent.EXTRA_TEXT, shareText)
//                `package` = targetPackage
//            }
//            context.startActivity(shareIntent)
//        } else {
//            Toast.makeText(context, "التطبيق المخصص غير مثبت", Toast.LENGTH_SHORT).show()
//        }
//    }) {
//        Text(text = "مشاركة مع التطبيق المخصص")
//    }
//}
//@Composable
//fun ShareImageButton() {
//    val context = LocalContext.current
//    val targetPackage = "com.discord" // ضع هنا اسم الحزمة للتطبيق المخصص
//    val imageFile = createSampleImage(context) // قم بإنشاء الصورة هنا
//
//    val imageUri: Uri = FileProvider.getUriForFile(
//        context,
//        "${context.packageName}.fileprovider",
//        imageFile
//    )
//
//    Button(onClick = {
//        if (isAppInstalled(context, targetPackage)) {
//            val shareIntent = Intent(Intent.ACTION_SEND).apply {
//                type = "image/jpeg"
//                putExtra(Intent.EXTRA_STREAM, imageUri)
//                `package` = targetPackage
//                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
//            }
//            context.startActivity(shareIntent)
//        } else {
//            Toast.makeText(context, "التطبيق المخصص غير مثبت", Toast.LENGTH_SHORT).show()
//        }
//    }) {
//        Text(text = "مشاركة الصورة مع التطبيق المخصص")
//    }
//}
//fun isAppInstalled(context: Context, packageName: String): Boolean {
//    return try {
//        context.packageManager.getPackageInfo(packageName, 0)
//        true
//    } catch (e: PackageManager.NameNotFoundException) {
//        false
//    }
//}
