package com.example.books.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import com.example.books.ui.theme.BooksTheme
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.books.R
import com.example.books.model.BookItem
import com.example.books.model.ImageLinks
import com.example.books.model.VolumeInfo

@Composable
fun BookDetailScreen(
    viewModel: BookViewModel
) {
    val state by viewModel.state.collectAsState()
    val selectedBook = state.selectedBook
    val scrollableState = rememberScrollState()

    if (selectedBook == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No Book Selected",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        }
        return
    }

    val info = selectedBook.volumeInfo

    Column(
        modifier = Modifier
            .verticalScroll(scrollableState)
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            // Blurred background image
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(info?.imageLinks?.thumbnail ?: "")
                    .crossfade(true)
                    .build(),
                contentDescription = info?.title,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxSize()
                    .blur(
                        radiusX = 10.dp,
                        radiusY = 10.dp,
                        edgeTreatment = BlurredEdgeTreatment.Unbounded
                    )
            )
            // Foreground image
            Box(
                modifier = Modifier
                    .offset(y = 75.dp)
                    .background(Color.White, RoundedCornerShape(12.dp))
                    .shadow(8.dp)
                    .width(150.dp)
                    .height(250.dp)
                    .zIndex(1f)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(info?.imageLinks?.thumbnail ?: "")
                        .crossfade(true)
                        .build(),
                    contentDescription = info?.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = info?.title ?: "Unknown Tille",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "by ${info?.authors?.joinToString() ?: "Unknown Author"}",
            style = MaterialTheme.typography.labelSmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = info?.publishedDate ?: "No description available.",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
            Text(
                text = info?.printType ?: "No description available.",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
            Text(
                text = info?.language ?: "No description available.",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 20.dp)
            )

        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "${info?.description}",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 20.dp, start = 20.dp, end = 20.dp)
        )
    }
}

// Preview
@Composable
fun BookDetailContent(book: BookItem) {
    val scrollableState = rememberScrollState()
    val info = book.volumeInfo

    Column(
        modifier = Modifier
            .verticalScroll(scrollableState)
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            // Blurred background image
            Image(
                painter = painterResource(R.drawable.the_island),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxSize()
                    .blur(
                        radiusX = 10.dp,
                        radiusY = 10.dp,
                        edgeTreatment = BlurredEdgeTreatment.Unbounded
                    )
            )

            // Foreground image wrapped in an isolated Box
            Box(
                modifier = Modifier
                    .offset(y = 75.dp)
                    .background(Color.White, RoundedCornerShape(12.dp))
                    .shadow(8.dp)
                    .width(150.dp)
                    .height(250.dp)
                    .zIndex(1f) // ensures it's drawn above the background
            ) {
                Image(
                    painter = painterResource(R.drawable.the_island),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = info?.title ?: "Unknown Title",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "by ${info?.authors?.joinToString() ?: "Unknown Author"}",
            style = MaterialTheme.typography.labelSmall
        )

        Spacer(modifier = Modifier.height(16.dp))
        Row(
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = info?.publishedDate ?: "No description available.",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
            Text(
                text = info?.printType ?: "No description available.",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
            Text(
                text = info?.language ?: "No description available.",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 20.dp)
            )

        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = info?.description ?: "No description available.",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(horizontal = 20.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BookDetailPreview() {
    val fakeBook = BookItem(
        id = "1",
        volumeInfo = VolumeInfo(
            title = "Preview Book",
            authors = listOf("Jane Doe"),
            publisher = "Preview Publisher",
            publishedDate = "2022",
            description = "This is a preview description of the book, covering the main points and providing insight into the content.",
            pageCount = 123,
            printType = "BOOK",
            categories = listOf("Preview Category"),
            averageRating = 4,
            ratingsCount = 100,
            imageLinks = ImageLinks(
                smallThumbnail = "https://unsplash.com/photos/a-stack-of-books-f80d5O78Bmo",
                thumbnail = "https://via.placeholder.com/150"
            ),
            language = "en"
        )
    )

    BookDetailContent(book = fakeBook)
}

