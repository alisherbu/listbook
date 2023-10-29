package kaa.alisherbu.listbook.feature.home.ui

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import kaa.alisherbu.listbook.core.shared.model.AudioBook

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun BookItem(
    audioBook: AudioBook,
    onAudioBookClick: (book: AudioBook) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = {
            onAudioBookClick(audioBook)
        },
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
