package com.example.sampleapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.example.sampleapp.testids.TestIds

// TODO(copilot): NavHost を組み Splash -> Login -> ItemList -> ItemDetail / Settings を配線
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize().testTag(TestIds.SCREEN_ROOT_SPLASH)) {
                    // TODO(copilot): AppNavGraph()
                }
            }
        }
    }
}
