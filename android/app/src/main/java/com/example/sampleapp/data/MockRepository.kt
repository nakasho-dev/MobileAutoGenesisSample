package com.example.sampleapp.data

data class Item(val id: String, val title: String, val description: String, val isFavorite: Boolean = false)

// TODO(copilot): docs/BASIC_DESIGN.md 5節のモックAPI実装。delay() のみでスタブ応答
object MockRepository {
    private val items = mutableListOf(
        Item("1", "First Item", "Description of the first item."),
        Item("2", "Second Item", "Description of the second item."),
        Item("3", "Third Item", "Description of the third item.")
    )

    suspend fun login(email: String, password: String): Boolean {
        kotlinx.coroutines.delay(500)
        return email == "demo@example.com" && password == "password"
    }

    suspend fun listItems(): List<Item> { kotlinx.coroutines.delay(300); return items.toList() }

    suspend fun getItem(id: String): Item? { kotlinx.coroutines.delay(200); return items.firstOrNull { it.id == id } }

    suspend fun toggleFavorite(id: String) {
        kotlinx.coroutines.delay(100)
        val i = items.indexOfFirst { it.id == id }
        if (i >= 0) items[i] = items[i].copy(isFavorite = !items[i].isFavorite)
    }
}
