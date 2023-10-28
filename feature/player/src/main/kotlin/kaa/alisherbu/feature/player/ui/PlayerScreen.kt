package kaa.alisherbu.feature.player.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import kaa.alisherbu.feature.player.component.PlayerComponent
import kaa.alisherbu.feature.player.store.PlayerState
import kaa.alisherbu.listbook.chapter.ui.ChapterScreen

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
                },
            )
        },
    ) {
        PlayerContent(
            state = state,
            onPreviousAudio = component::onPreviousAudio,
            onPlayPauseAudio = component::onPlayPauseAudio,
            onNextAudio = component::onNextAudio,
            onUserPositionChange = component::onUserPositionChange,
            onUserPositionChangeFinished = component::onUserPositionChangeFinished,
            onDownloadClick = component::onDownloadClick,
            onRemoveClick = component::onRemoveClick,
            onChapterClick = component::onChapterClick,
            modifier = Modifier.padding(it)
        )
    }

    val dialogSlot by component.dialogSlot.subscribeAsState()
    dialogSlot.child?.instance?.also { childDialog ->
        when (childDialog) {
            is PlayerComponent.ChildDialog.ChapterDialog -> {
                ModalBottomSheet(onDismissRequest = component::onChapterDismiss) {
                    ChapterScreen(component = childDialog.component)
                }
            }
        }
    }
}

@Composable
private fun PlayerContent(
    state: PlayerState,
    onPreviousAudio: () -> Unit,
    onPlayPauseAudio: () -> Unit,
    onNextAudio: () -> Unit,
    onUserPositionChange: (Long) -> Unit,
    onUserPositionChangeFinished: () -> Unit,
    onDownloadClick: () -> Unit,
    onRemoveClick: () -> Unit,
    onChapterClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
    ) {
        val (playerController, playerSlider, bookName, download, chapter) = createRefs()

        state.currentAudioBook?.let {
            Text(
                text = it.name,
                modifier = Modifier.constrainAs(bookName) {
                    linkTo(start = parent.start, end = parent.end)
                    bottom.linkTo(playerSlider.top, margin = 16.dp)
                },
            )
            if (it.isDownloaded) {
                TextButton(
                    onClick = onRemoveClick,
                    modifier = Modifier.constrainAs(download) {
                        linkTo(start = parent.start, end = parent.end)
                        bottom.linkTo(bookName.top, margin = 16.dp)
                    },
                ) {
                    Text(text = "Remove")
                }
            } else {
                TextButton(
                    onClick = onDownloadClick,
                    modifier = Modifier.constrainAs(download) {
                        linkTo(start = parent.start, end = parent.end)
                        bottom.linkTo(bookName.top, margin = 16.dp)
                    },
                ) {
                    Text(text = "Download")
                }
            }
        }
        PlayerSlider(
            position = state.position,
            positionText = state.positionText,
            userPosition = state.userPosition,
            userPositionText = state.userPositionText,
            duration = state.duration,
            durationText = state.durationText,
            onUserPositionChange = onUserPositionChange,
            onUserPositionChangeFinish = onUserPositionChangeFinished,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .constrainAs(playerSlider) {
                    bottom.linkTo(playerController.top, margin = 16.dp)
                },
        )
        PlayerController(
            isPlaying = state.isPlaying,
            onPrevious = onPreviousAudio,
            onPlayPause = onPlayPauseAudio,
            onNext = onNextAudio,
            modifier = Modifier.constrainAs(playerController) {
                width = Dimension.matchParent
                bottom.linkTo(chapter.top, margin = 16.dp)
            },
        )
        TextButton(onClick = onChapterClick,
            modifier = Modifier.constrainAs(chapter) {
                bottom.linkTo(parent.bottom)
            }) {
            Text(text = "Chapter")
        }
    }
}

