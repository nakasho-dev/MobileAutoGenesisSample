package com.example.sampleapp.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sampleapp.data.Item
import com.example.sampleapp.data.MockRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface ItemListState {
    data object Loading : ItemListState
    data object Empty : ItemListState
    data class Success(val items: List<Item>) : ItemListState
    data object Error : ItemListState
}

class ItemListViewModel : ViewModel() {
    private val _state = MutableStateFlow<ItemListState>(ItemListState.Loading)
    val state: StateFlow<ItemListState> = _state.asStateFlow()

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            _state.value = ItemListState.Loading
            _state.value = runCatching { MockRepository.listItems() }
                .fold(
                    onSuccess = { items ->
                        if (items.isEmpty()) ItemListState.Empty else ItemListState.Success(items)
                    },
                    onFailure = { ItemListState.Error },
                )
        }
    }
}