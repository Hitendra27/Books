package com.example.books.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.request.ImageRequest
import com.example.books.model.BookApiResponse
import com.example.books.model.BookItem
import com.example.books.model.ImageLinks
import com.example.books.model.VolumeInfo
import com.example.books.repository.BookRepository

@Composable
fun BookListScreen(
    viewModel: BookViewModel,
    onBookSelected: (String) -> Unit
) {
    val state by viewModel.state.collectAsState()
    var searchQuery by rememberSaveable { mutableStateOf(viewModel.currentQuery) }

    Column(modifier = Modifier.fillMaxSize()) {

        // Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = {
                searchQuery = it
                viewModel.updateQuery(it)
            },
            label = { Text("Search books") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            singleLine = true
        )

        // Grid of Books
        LazyVerticalGrid(
            columns = GridCells.Adaptive(150.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)
        ) {
            val books = state.data?.items ?: emptyList()

            items(books.size) { index ->
                val book = books[index]
                BookCard(
                    photo = book,
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth(),
                        onClick =  {
                            book.id?.let { onBookSelected(it) }
                        }
                )
            }
        }

        // Loading indicator
        if (state.isLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        // ⚠ Error
        state.error?.let {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Error: $it", color = MaterialTheme.colorScheme.error)
            }
        }
    }
}



    @Composable
    fun BookCard(
        photo: BookItem,
        modifier: Modifier = Modifier,
        onClick: () -> Unit
    ) {
        Card(
            modifier = modifier
                .clickable { onClick()}
                .padding(4.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(photo.volumeInfo?.imageLinks?.thumbnail)
                    .crossfade(true)
                    .build(),
                contentDescription = photo.volumeInfo?.title,
                contentScale = ContentScale.None,
                modifier = Modifier
                    .fillMaxSize()
                    .aspectRatio(0.75f)
            )
            Text(
                text = photo.volumeInfo?.title ?: "No Title",
                modifier = Modifier.padding(8.dp),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }



// A plain function that returns a fake ViewModel (no @Composable)
fun createFakeBookViewModel(): BookViewModel {
    val fakeRepo = object : BookRepository {
        override suspend fun getBooks(query: String): BookApiResponse = BookApiResponse(
            items = listOf(
                BookItem(
                    id = "1",
                    volumeInfo = VolumeInfo(
                        title = "The Great Gatsby",
                        authors = listOf("F. Scott Fitzgerald"),
                        publisher = "Scribner",
                        publishedDate = "1925",
                        description = "A novel set in the Jazz Age that tells the story of Jay Gatsby.",
                        pageCount = 180,
                        printType = "BOOK",
                        categories = listOf("Fiction", "Classics"),
                        averageRating = 4,
                        ratingsCount = 8500,
                        imageLinks = ImageLinks(
                            smallThumbnail = "https://via.placeholder.com/150/0000FF/808080?Text=Gatsby",
                            thumbnail = "https://via.placeholder.com/150/0000FF/808080?Text=Gatsby"
                        ),
                        language = "en"
                    )
                ),
                BookItem(
                    id = "2",
                    volumeInfo = VolumeInfo(
                        title = "1984",
                        authors = listOf("George Orwell"),
                        publisher = "Secker & Warburg",
                        publishedDate = "1949",
                        description = "A dystopian novel about totalitarian regime and surveillance.",
                        pageCount = 328,
                        printType = "BOOK",
                        categories = listOf("Dystopian", "Science Fiction"),
                        averageRating = 5,
                        ratingsCount = 12000,
                        imageLinks = ImageLinks(
                            smallThumbnail = "https://via.placeholder.com/150/FF0000/FFFFFF?Text=1984",
                            thumbnail = "https://via.placeholder.com/150/FF0000/FFFFFF?Text=1984"
                        ),
                        language = "en"
                    )
                ),
                BookItem(
                    id = "3",
                    volumeInfo = VolumeInfo(
                        title = "To Kill a Mockingbird",
                        authors = listOf("Harper Lee"),
                        publisher = "J.B. Lippincott & Co.",
                        publishedDate = "1960",
                        description = "A novel about racial injustice in the Deep South.",
                        pageCount = 281,
                        printType = "BOOK",
                        categories = listOf("Fiction", "Historical"),
                        averageRating = 5,
                        ratingsCount = 11000,
                        imageLinks = ImageLinks(
                            smallThumbnail = "https://via.placeholder.com/150/00FF00/000000?Text=Mockingbird",
                            thumbnail = "https://via.placeholder.com/150/00FF00/000000?Text=Mockingbird"
                        ),
                        language = "en"
                    )
                )
            )
        )

        override suspend fun getBookById(id: String): BookItem {
            return BookItem(id = id, volumeInfo = null)
        }
    }
    return BookViewModel(fakeRepo)
}

@Preview(showBackground = true)
@Composable
fun BookListScreenPreview() {
    val fakeViewModel = remember { createFakeBookViewModel() }

    LaunchedEffect(Unit) {
        fakeViewModel.loadBooks()  // launches suspend function inside coroutine scope
    }

    BookListScreen(
        viewModel = fakeViewModel,
        onBookSelected = {}
    )
}












