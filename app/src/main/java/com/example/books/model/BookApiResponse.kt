package com.example.books.model

data class BookApiResponse(
    val items: List<BookItem>?
)

data class BookItem(
    val id: String?,
    val volumeInfo: VolumeInfo?
)

data class VolumeInfo(
    val title: String?,
    val authors: List<String>?,
    val publisher: String?,
    val publishedDate: String?,
    val description: String?,
    val pageCount: Int?,
    val printType: String?,
    val categories: List<String>?,
    val averageRating: Int?,
    val ratingsCount: Int?,
    val imageLinks: ImageLinks?,
    val language: String?
)

data class ImageLinks(
    val smallThumbnail: String?,
    val thumbnail: String?
)
