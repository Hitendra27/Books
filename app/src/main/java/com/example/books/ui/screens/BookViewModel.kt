package com.example.books.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.books.model.BookApiResponse
import com.example.books.model.BookItem
import com.example.books.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(
    private val repository: BookRepository
) : ViewModel() {

    private val _state = MutableStateFlow(BookUiState())
    val state: StateFlow<BookUiState> = _state

    var currentQuery = "jazz history"
    private set

    // search query for search bar
    fun updateQuery(query: String) {
        currentQuery = query
        loadBooks(query)
    }

    // function to get book eg Jazz history
    fun loadBooks(query: String = currentQuery) {
        _state.value = BookUiState(isLoading = true)

        viewModelScope.launch {
            try {
                val result = repository.getBooks(query)
                _state.value = state.value.copy(
                    isLoading = false,
                    data = result,
                    error = null
                )
            } catch (e: Exception) {
                _state.value = BookUiState(error = e.message ?: "Unknown error")
            }
        }
    }

    // function to get book by Id
    fun loadBookById(id: String) {
        _state.value = _state.value.copy(isLoading = true)

        viewModelScope.launch {
            try {
                val book = repository.getBookById(id)
                _state.value = _state.value.copy(
                    isLoading = false,
                    selectedBook = book,
                    error = null
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message ?: "Unknown error"
                )
            }
        }
    }
}

data class BookUiState (
    val isLoading: Boolean = false,
    val data: BookApiResponse? = null,
    val selectedBook: BookItem?= null,
    val error: String? = null
)

