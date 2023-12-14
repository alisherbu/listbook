package kaa.alisherbu.feature.player.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
            onUserPositionChangeFinish = component::onUserPositionChangeFinished,
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
@Suppress("LongParameterList")
private fun PlayerContent(
    state: PlayerState,
    onPreviousAudio: () -> Unit,
    onPlayPauseAudio: () -> Unit,
    onNextAudio: () -> Unit,
    onUserPositionChange: (Long) -> Unit,
    onUserPositionChangeFinish: () -> Unit,
    onDownloadClick: () -> Unit,
    onRemoveClick: () -> Unit,
    onChapterClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
    ) {
        val (playerController, playerSlider, bookName) = createRefs()
        val (chapterName, download, chapter) = createRefs()

        state.currentChapter?.let {
            Text(
                text = it.name,
                modifier = Modifier.constrainAs(chapterName) {
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
        state.currentAudioBook?.let {
            Text(
                text = it.name,
                fontSize = 20.sp,
                modifier = Modifier.constrainAs(bookName) {
                    linkTo(start = parent.start, end = parent.end)
                    bottom.linkTo(chapterName.top, margin = 16.dp)
                },
            )
        }
        PlayerSlider(
            position = state.position,
            positionText = state.positionText,
            userPosition = state.userPosition,
            userPositionText = state.userPositionText,
            duration = state.duration,
            durationText = state.durationText,
            onUserPositionChange = onUserPositionChange,
            onUserPositionChangeFinish = onUserPositionChangeFinish,
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
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(60.dp)
                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                .background(Color.LightGray)
                .clickable { onChapterClick() }
                .constrainAs(chapter) {
                    bottom.linkTo(parent.bottom)
                }
        ) {
            Text(
                text = "Chapter",
                modifier = Modifier
                    .align(Alignment.Center),
            )
        }
    }
}
