package pl.tablice.lookup.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pl.tablice.lookup.data.PlateEntry
import pl.tablice.lookup.data.PlateRepository

class PlateViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = PlateRepository()

    private var allEntries: List<PlateEntry> = emptyList()

    private val _uiState = MutableStateFlow(PlateUiState())
    val uiState: StateFlow<PlateUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            allEntries = withContext(Dispatchers.IO) { repository.loadFromAssets(getApplication()) }
            _uiState.value = _uiState.value.copy(results = filterPlates(allEntries, ""))
        }
    }

    fun onQueryChange(rawInput: String) {
        val sanitized = sanitizePlateQuery(rawInput)
        _uiState.value = _uiState.value.copy(
            query = sanitized,
            results = filterPlates(allEntries, sanitized)
        )
    }
}
