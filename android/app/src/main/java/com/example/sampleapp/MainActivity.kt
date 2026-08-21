package com.example.sampleapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import com.example.sampleapp.data.Item
import com.example.sampleapp.ui.detail.ItemDetailScreen
import com.example.sampleapp.ui.list.ItemListScreen
import com.example.sampleapp.ui.login.LoginScreen
import com.example.sampleapp.ui.settings.SettingsScreen
import com.example.sampleapp.ui.splash.SplashScreen
import kotlinx.coroutines.delay

@OptIn(ExperimentalComposeUiApi::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var isDarkTheme by remember { mutableStateOf(false) }

            MaterialTheme(
                colorScheme = if (isDarkTheme) darkColorScheme() else lightColorScheme(),
            ) {
                var destination by remember { mutableStateOf(Destination.Splash) }
                var selectedItem by remember { mutableStateOf<Item?>(null) }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .semantics { testTagsAsResourceId = true },
                ) {
                    when (destination) {
                        Destination.Splash -> {
                            LaunchedEffect(Unit) {
                                delay(2_000)
                                destination = Destination.Login
                            }
                            SplashScreen()
                        }
                        Destination.Login -> LoginScreen(
                            onLoginSuccess = { destination = Destination.ItemList },
                        )
                        Destination.ItemList -> ItemListScreen(
                            onItemClick = { item ->
                                selectedItem = item
                                destination = Destination.ItemDetail
                            },
                            onSettingsClick = { destination = Destination.Settings },
                        )
                        Destination.ItemDetail -> selectedItem?.let { item ->
                            ItemDetailScreen(
                                itemId = item.id,
                                onBack = { destination = Destination.ItemList },
                            )
                        }
                        Destination.Settings -> SettingsScreen(
                            isDarkTheme = isDarkTheme,
                            onThemeChange = { isDarkTheme = it },
                            onSignOut = {
                                selectedItem = null
                                destination = Destination.Login
                            },
                        )
                    }
                }
            }
        }
    }
}

private enum class Destination {
    Splash,
    Login,
    ItemList,
    ItemDetail,
    Settings,
}
