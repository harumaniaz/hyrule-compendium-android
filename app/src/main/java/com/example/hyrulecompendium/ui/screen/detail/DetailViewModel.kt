package com.example.hyrulecompendium.ui.screen.detail

import androidx.lifecycle.ViewModel
import com.example.hyrulecompendium.data.remote.Entry
import com.example.hyrulecompendium.data.repository.ApiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class DetailUiState(
    var entry: Entry? = null,
)

class DetailViewModel(
    private val repository: ApiRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState = _uiState.asStateFlow()

    fun getEntry(id: Int) {
        val entry = repository.getEntryInCache(id)
        entry?.let {
            _uiState.update { it.copy(entry = entry) }
        }
    }
}