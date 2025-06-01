package com.example.books.repository

import com.example.books.model.BookApiResponse
import com.example.books.model.BookItem


interface BookRepository {
    suspend fun getBooks(query: String) : BookApiResponse
    suspend fun getBookById(id: String) : BookItem
}