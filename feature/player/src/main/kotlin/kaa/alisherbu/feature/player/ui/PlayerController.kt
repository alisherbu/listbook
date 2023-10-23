package kaa.alisherbu.feature.player.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun PlayerController(modifier: Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        FilledTonalIconButton(onClick = { }) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
        }

        Spacer(modifier = Modifier.width(16.dp))

        FilledTonalIconButton(onClick = { }) {
            Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "")
        }

        Spacer(modifier = Modifier.width(16.dp))
        
        FilledTonalIconButton(onClick = { }) {
            Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "")
        }
    }
}

@Preview
@Composable
private fun PlayerControllerPreview() {
    PlayerController(modifier = Modifier.fillMaxWidth())
}