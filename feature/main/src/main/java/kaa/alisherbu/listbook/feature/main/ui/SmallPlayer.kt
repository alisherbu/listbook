package kaa.alisherbu.listbook.feature.main.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import kaa.alisherbu.listbook.core.shared.model.AudioBook
import kaa.alisherbu.listbook.core.shared.R as Shared

@Composable
internal fun SmallPlayer(
    isPlaying: Boolean,
    audioBook: AudioBook,
    modifier: Modifier = Modifier,
    onPlayOrPause: () -> Unit = {},
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.LightGray)
            .heightIn(min = 60.dp),
    ) {
        val (audioName, playOrPause) = createRefs()
        Text(
            text = audioBook.name,
            modifier = Modifier.constrainAs(audioName) {
                linkTo(top = parent.top, bottom = parent.bottom)
                start.linkTo(parent.start, margin = 16.dp)
            },
        )

        IconButton(
            onClick = onPlayOrPause,
            modifier = Modifier.constrainAs(playOrPause) {
                linkTo(top = parent.top, bottom = parent.bottom)
                end.linkTo(parent.end, margin = 16.dp)
            },
        ) {
            Icon(
                painter = painterResource(
                    if (isPlaying) {
                        Shared.drawable.ic_pause_24
                    } else {
                        Shared.drawable.ic_play_arrow_24
                    },
                ),
                contentDescription = "Play&Pause",
            )
        }
    }
}

@Preview
@Composable
private fun SmallPlayerPreview() {
    val audioBook = AudioBook(id = "id", name = "Name", audioUrl = "url")
    SmallPlayer(isPlaying = true, audioBook = audioBook)
}
