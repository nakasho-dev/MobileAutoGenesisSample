package com.example.sampleapp.ui.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sampleapp.R
import com.example.sampleapp.testids.TestIds

@Composable
fun ItemDetailScreen(
    itemId: String,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ItemDetailViewModel = viewModel(),
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(itemId) {
        viewModel.load(itemId)
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .testTag(TestIds.SCREEN_ROOT_ITEM_DETAIL),
        topBar = {
            Text(
                text = stringResource(R.string.item_detail_title),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                style = MaterialTheme.typography.headlineSmall,
            )
        },
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(contentPadding)
                .padding(horizontal = 28.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            when (val currentState = state) {
                ItemDetailState.Loading -> CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                )
                ItemDetailState.Error -> Text(
                    text = stringResource(R.string.item_detail_error),
                    color = MaterialTheme.colorScheme.error,
                )
                is ItemDetailState.Success -> {
                    Text(
                        text = currentState.item.title,
                        modifier = Modifier.testTag(TestIds.ITEM_DETAIL_TITLE),
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Text(
                        text = currentState.item.description,
                        modifier = Modifier.testTag(TestIds.ITEM_DETAIL_DESCRIPTION),
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(stringResource(R.string.item_detail_favorite))
                        Switch(
                            checked = currentState.item.isFavorite,
                            onCheckedChange = { viewModel.toggleFavorite() },
                            modifier = Modifier.testTag(TestIds.ITEM_DETAIL_FAVORITE_TOGGLE),
                        )
                    }
                }
            }

            Button(
                onClick = onBack,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag(TestIds.ITEM_DETAIL_BACK_BUTTON),
            ) {
                Text(stringResource(R.string.item_detail_back))
            }
        }
    }
}