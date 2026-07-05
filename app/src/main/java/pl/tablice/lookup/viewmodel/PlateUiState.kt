package pl.tablice.lookup.viewmodel

import pl.tablice.lookup.data.PlateEntry

data class PlateUiState(
    val query: String = "",
    val results: List<PlateEntry> = emptyList()
)
