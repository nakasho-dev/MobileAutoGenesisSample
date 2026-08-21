package com.example.sampleapp.ui.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sampleapp.R
import com.example.sampleapp.data.Item
import com.example.sampleapp.testids.TestIds

@Composable
fun ItemListScreen(
    onItemClick: (Item) -> Unit,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ItemListViewModel = viewModel(),
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .testTag(TestIds.SCREEN_ROOT_ITEM_LIST),
        topBar = {
            Text(
                text = stringResource(R.string.item_list_title),
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
            verticalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            Button(
                onClick = viewModel::refresh,
                modifier = Modifier.testTag(TestIds.ITEM_LIST_PULL_TO_REFRESH),
                enabled = state !is ItemListState.Loading,
            ) {
                Text(stringResource(R.string.item_list_refresh))
            }

            when (val currentState = state) {
                ItemListState.Loading -> CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                )
                ItemListState.Empty -> Text(stringResource(R.string.item_list_empty))
                ItemListState.Error -> Text(
                    text = stringResource(R.string.item_list_error),
                    color = MaterialTheme.colorScheme.error,
                )
                is ItemListState.Success -> currentState.items.forEachIndexed { index, item ->
                    OutlinedCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onItemClick(item) }
                            .testTag(TestIds.itemListRow(index)),
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Text(item.title)
                            Text(">")
                        }
                    }
                }
            }

            Button(
                onClick = onSettingsClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag(TestIds.ITEM_LIST_SETTINGS_BUTTON),
            ) {
                Text(stringResource(R.string.item_list_settings))
            }
        }
    }
}