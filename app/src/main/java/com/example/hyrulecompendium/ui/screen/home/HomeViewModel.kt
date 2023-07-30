package com.example.hyrulecompendium.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hyrulecompendium.data.CategoryType
import com.example.hyrulecompendium.data.remote.ApiError
import com.example.hyrulecompendium.data.remote.ApiException
import com.example.hyrulecompendium.data.remote.ApiSuccess
import com.example.hyrulecompendium.data.remote.Entry
import com.example.hyrulecompendium.data.repository.ApiRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

class HomeViewModel(
    private val repository: ApiRepository
) : ViewModel() {
    data class UiState(
        var entryList: List<Entry> = emptyList(),
        var categoryList: List<CategoryType> = listOf(*CategoryType.values()),
        var selectedCategory: CategoryType = CategoryType.CREATURES,
        var isLoading: Boolean = false
    )

    sealed class UiEvent {
        object None : UiEvent()
        class FetchError(val detail: String?) : UiEvent()
    }

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    init {
        getAllEntries()
    }

    private fun getAllEntries() {
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            when (val result = repository.getAllEntries()) {
                is ApiSuccess -> {
                    val entryList = result.data.data
                        .filter { it.category == _uiState.value.selectedCategory.id }
                        .sortedBy { it.id }
                    _uiState.update { it.copy(entryList = entryList) }
                }

                is ApiError -> {
                    Timber.e("code=${result.code} message=${result.message}")
                    _uiEvent.emit(UiEvent.FetchError(result.message))
                }

                is ApiException -> {
                    Timber.e(result.exception)
                    _uiEvent.emit(UiEvent.FetchError(result.exception.message))
                }
            }

            _uiState.update { it.copy(isLoading = false) }
        }
    }

    fun setCategoryFilter(category: CategoryType) {
        if (_uiState.value.selectedCategory == category) return

        val entryList = repository.getAllEntriesInCache()
            .filter { it.category == category.id }
            .sortedBy { it.id }

        _uiState.update {
            it.copy(
                entryList = entryList,
                selectedCategory = category
            )
        }

    }
}