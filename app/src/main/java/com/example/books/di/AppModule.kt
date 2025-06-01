package com.example.books.di

import com.example.books.data.BookApi
import com.example.books.repository.BookRepository
import com.example.books.repository.BookRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideBookApi() : BookApi =
        Retrofit.Builder()
            .baseUrl("https://www.googleapis.com/books/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BookApi::class.java)

    @Provides
    @Singleton
    fun provideBookRepository(api: BookApi) : BookRepository =
        BookRepositoryImpl(api)
}