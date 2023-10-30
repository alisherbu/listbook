package kaa.alisherbu.listbook.feature.home.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import kaa.alisherbu.listbook.core.shared.model.AudioBook

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun BookDetailItem(
    audioBook: AudioBook,
    onAudioBookClick: (book: AudioBook) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = {
            onAudioBookClick(audioBook)
        },
        modifier = modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        shape = RoundedCornerShape(8.dp),
    ) {
        Row {
            AsyncImage(
                model = audioBook.headerImage,
                contentDescription = "",
                modifier = Modifier
                    .height(140.dp)
                    .width(100.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = audioBook.name)
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "There will be book description")
            }
        }
    }
}