package com.example.hyrulecompendium.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hyrulecompendium.data.CategoryType
import com.example.hyrulecompendium.data.Entry
import com.example.hyrulecompendium.data.repository.EntryRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

class HomeViewModel(
    private val repository: EntryRepository
) : ViewModel() {
    data class UiState(
        var entries: List<Entry> = emptyList(),
        var categories: List<CategoryType> = listOf(*CategoryType.values()),
        var selectedCategory: CategoryType = CategoryType.CREATURES,
        var isLoading: Boolean = false
    )

    sealed class UiEvent {
        object None : UiEvent()
        class FetchError : UiEvent()
    }

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    private var allEntries: List<Entry> = emptyList()

    init {
        getAllEntries()
    }

    private fun getAllEntries() {
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            runCatching { repository.getAllEntries() }
                .onSuccess { result ->
                    allEntries = result.sortedBy { it.id }

                    val entries = allEntries
                        .filter { it.category == _uiState.value.selectedCategory.id }
                    _uiState.update { it.copy(entries = entries) }
                }
                .onFailure {
                    Timber.e(it)
                    _uiEvent.emit(UiEvent.FetchError())
                }

            _uiState.update { it.copy(isLoading = false) }
        }
    }

    fun setCategoryFilter(category: CategoryType) {
        if (_uiState.value.selectedCategory == category) return

        val entries = allEntries.filter { it.category == category.id }
        _uiState.update {
            it.copy(
                entries = entries,
                selectedCategory = category
            )
        }
    }
}