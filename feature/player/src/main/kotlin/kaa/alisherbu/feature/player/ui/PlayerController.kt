package kaa.alisherbu.feature.player.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kaa.alisherbu.listbook.feature.player.R

@Composable
fun PlayerController(
    modifier: Modifier,
    isPlaying: Boolean,
    onPrevious: () -> Unit = {},
    onPlayPause: () -> Unit = {},
    onNext: () -> Unit = {}
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        FilledTonalIconButton(
            onClick = onPrevious,
            modifier = Modifier.size(50.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.baseline_skip_previous_24),
                contentDescription = "Previous"
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        FilledTonalIconButton(
            onClick = onPlayPause,
            modifier = Modifier.size(75.dp)
        ) {
            Icon(
                painter = if (isPlaying) painterResource(R.drawable.baseline_pause_24)
                else painterResource(R.drawable.baseline_play_arrow_24),
                contentDescription = "Play&Pause"
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        FilledTonalIconButton(
            onClick = onNext,
            modifier = Modifier.size(50.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.baseline_skip_next_24),
                contentDescription = "Next"
            )
        }
    }
}

@Preview
@Composable
private fun PlayerControllerPreview() {
    PlayerController(modifier = Modifier.fillMaxWidth(), isPlaying = true)
}