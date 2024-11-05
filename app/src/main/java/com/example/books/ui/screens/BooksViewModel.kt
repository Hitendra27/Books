package com.example.books.ui.screens

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class BooksViewModel : ViewModel() {
    /** The mutable State that stores the status of the most recent request */
    var booksUiState: String by mutableStateOf("")
        private set

    /**
     * Call getBooks() on init so we can display status immediately.
     */
    init {
        getBooks()
    }

    /**
     * Get Books information from the Books API Retrofit service and update the
     * [Books] [List] [MutableList]
     */

    fun getBooks() {
        booksUiState = "Set the Books API status response here!"
    }
}