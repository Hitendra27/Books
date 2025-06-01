package com.example.books.repository

import com.example.books.data.BookApi
import com.example.books.model.BookApiResponse
import com.example.books.model.BookItem
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(
    private val api: BookApi
) : BookRepository{

    override suspend fun getBooks(query: String): BookApiResponse {
        return api.getBooks(query)
    }

    override suspend fun getBookById(id: String): BookItem {
        return api.getBookById(id)
    }
}