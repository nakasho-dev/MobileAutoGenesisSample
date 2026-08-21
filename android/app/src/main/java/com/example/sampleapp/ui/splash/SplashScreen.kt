package com.example.sampleapp.ui.splash

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.sampleapp.R
import com.example.sampleapp.testids.TestIds

@Composable
fun SplashScreen(modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .testTag(TestIds.SCREEN_ROOT_SPLASH),
        topBar = {
            Text(
                text = stringResource(R.string.splash_title),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                style = MaterialTheme.typography.headlineSmall,
            )
        },
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(40.dp, Alignment.CenterVertically),
        ) {
            Box(
                modifier = Modifier
                    .size(92.dp)
                    .border(3.dp, MaterialTheme.colorScheme.primary, CircleShape)
                    .testTag(TestIds.SPLASH_LOGO),
                contentAlignment = Alignment.Center,
            ) {
                Text(stringResource(R.string.splash_logo))
            }
            Text(stringResource(R.string.splash_transition_hint))
        }
    }
}