package kaa.alisherbu.listbook.feature.home.ui

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import kaa.alisherbu.listbook.core.shared.model.AudioBook

@Composable
internal fun BookItem(audioBook: AudioBook, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.padding(vertical = 2.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        shape = RoundedCornerShape(8.dp),
    ) {
        AsyncImage(
            model = audioBook.headerImage,
            contentDescription = "",
            modifier = Modifier
                .height(140.dp)
                .width(100.dp),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
@Preview
private fun BookItemPreview() {
    val audioBook = AudioBook(
        id = "1",
        name = "Chapter 1",
        audioUrl = "",
        headerImage = "",
        isDownloaded = false
    )
    BookItem(audioBook = audioBook)
}