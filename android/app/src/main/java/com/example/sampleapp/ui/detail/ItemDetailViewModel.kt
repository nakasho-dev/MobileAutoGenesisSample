package com.example.sampleapp.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sampleapp.data.Item
import com.example.sampleapp.data.MockRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface ItemDetailState {
    data object Loading : ItemDetailState
    data class Success(val item: Item) : ItemDetailState
    data object Error : ItemDetailState
}

class ItemDetailViewModel : ViewModel() {
    private val _state = MutableStateFlow<ItemDetailState>(ItemDetailState.Loading)
    val state: StateFlow<ItemDetailState> = _state.asStateFlow()

    private var itemId: String? = null

    fun load(id: String) {
        if (itemId == id && _state.value is ItemDetailState.Success) return
        itemId = id
        viewModelScope.launch {
            _state.value = ItemDetailState.Loading
            _state.value = MockRepository.getItem(id)
                ?.let(ItemDetailState::Success)
                ?: ItemDetailState.Error
        }
    }

    fun toggleFavorite() {
        val id = itemId ?: return
        viewModelScope.launch {
            MockRepository.toggleFavorite(id)
            _state.value = MockRepository.getItem(id)
                ?.let(ItemDetailState::Success)
                ?: ItemDetailState.Error
        }
    }
}