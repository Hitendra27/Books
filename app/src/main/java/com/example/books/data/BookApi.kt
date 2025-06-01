package com.example.books.data

import com.example.books.model.BookApiResponse
import com.example.books.model.BookItem
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BookApi {

    @GET("volumes")
    suspend fun getBooks(
        @Query("q") query: String
    ) : BookApiResponse

    @GET("volumes/{volumeId}")
    suspend fun getBookById(
        @Path("volumeId") id: String
    ) : BookItem
}