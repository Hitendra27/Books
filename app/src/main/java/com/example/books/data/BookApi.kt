package com.example.books.data

import com.example.books.model.BookItem
import com.example.books.model.BooksApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BookApi {

    @GET("volumes")
    suspend fun getBooks(
        @Query("q") query: String
    ) : BooksApiResponse

    @GET("volumes/{volumeId}")
    suspend fun getBookById(
        @Path("volumeId") id: String
    ) : BookItem
}