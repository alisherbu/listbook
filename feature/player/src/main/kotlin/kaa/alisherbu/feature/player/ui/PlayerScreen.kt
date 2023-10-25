package kaa.alisherbu.feature.player.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import kaa.alisherbu.feature.player.component.PlayerComponent
import kaa.alisherbu.feature.player.store.PlayerState
import kaa.alisherbu.listbook.core.shared.model.AudioBook
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerScreen(component: PlayerComponent) {
    val state by component.state.collectAsState()
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Now Playing")
                },
                navigationIcon = {
                    IconButton(onClick = component::onBackClicked) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                })
        }) { padding ->
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            val (playerController, playerSlider, bookName, download) = createRefs()

            state.currentAudioBook?.let {
                Text(text = it.name, modifier = Modifier.constrainAs(bookName) {
                    linkTo(start = parent.start, end = parent.end)
                    bottom.linkTo(playerSlider.top, margin = 16.dp)
                })

                TextButton(
                    onClick = component::onDownloadClick,
                    modifier = Modifier.constrainAs(download) {
                        linkTo(start = parent.start, end = parent.end)
                        bottom.linkTo(bookName.top, margin = 16.dp)
                    }
                ) {
                    Text(text = "Download")
                }
            }
            PlayerSlider(
                position = state.position,
                positionText = state.positionText,
                userPosition = state.userPosition,
                userPositionText = state.userPositionText,
                duration = state.duration,
                durationText = state.durationText,
                onUserPositionChange = component::onUserPositionChange,
                onUserPositionChangeFinished = component::onUserPositionChangeFinished,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .constrainAs(playerSlider) {
                        bottom.linkTo(playerController.top, margin = 16.dp)
                    }
            )
            PlayerController(
                isPlaying = state.isPlaying,
                onPrevious = component::onPreviousAudio,
                onPlayPause = component::onPlayPauseAudio,
                onNext = component::onNextAudio,
                modifier = Modifier.constrainAs(playerController) {
                    width = Dimension.matchParent
                    bottom.linkTo(parent.bottom, margin = 16.dp)
                })
        }
    }
}

@Preview
@Composable
private fun PlayerScreenPreview() {
    PlayerScreen(PreviewPlayerComponent())
}

private class PreviewPlayerComponent : PlayerComponent {
    val playerState = PlayerState(
        duration = 10000,
        durationText = "06:50",
        position = 4000,
        positionText = "03:45",
        currentAudioBook = AudioBook("id", "Audio book name", "")
    )
    override val state: StateFlow<PlayerState> = MutableStateFlow(playerState)
    override fun onBackClicked() = Unit
    override fun onPreviousAudio() = Unit
    override fun onPlayPauseAudio() = Unit
    override fun onNextAudio() = Unit
    override fun onUserPositionChange(value: Long) = Unit
    override fun onUserPositionChangeFinished() = Unit
    override fun onDownloadClick() = Unit

}