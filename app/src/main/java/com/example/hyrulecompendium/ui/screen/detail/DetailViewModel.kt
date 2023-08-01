package com.example.hyrulecompendium.ui.screen.detail

import androidx.lifecycle.ViewModel
import com.example.hyrulecompendium.data.Entry
import com.example.hyrulecompendium.data.repository.EntryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class DetailViewModel(
    private val repository: EntryRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    data class UiState(
        var entry: Entry? = null,
    )

    suspend fun getEntry(id: Int) {
        repository.getEntry(id)?.let {
            _uiState.value.entry = it
        }
    }
}