package kaa.alisherbu.feature.player.store

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kaa.alisherbu.service.player.AudioPlayer
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

internal class PlayerExecutor @Inject constructor(
    private val audioPlayer: AudioPlayer,
) : CoroutineExecutor<Intent, Action, PlayerState, Message, Label>() {
    private val formatter = SimpleDateFormat("mm:ss", Locale.getDefault())

    init {
        audioPlayer.isPlaying.onEach {
            dispatch(Message.PlayOrPause(it))
        }.launchIn(scope)

        audioPlayer.currentChapter.onEach {
            checkNotNull(it) { "Chapter shouldn't be null in the player" }
            dispatch(Message.UpdateChapter(it))
        }.launchIn(scope)

        audioPlayer.currentPosition.onEach {
            dispatch(
                Message.UpdateCurrentPosition(
                    position = it,
                    positionText = formatter.format(it),
                ),
            )
        }.launchIn(scope)

        audioPlayer.duration.onEach {
            if (it < 0) return@onEach
            dispatch(
                Message.UpdateDuration(
                    duration = it,
                    durationText = formatter.format(it),
                ),
            )
        }.launchIn(scope)

    }

    override fun executeIntent(intent: Intent, getState: () -> PlayerState) {
        when (intent) {
            Intent.PlayOrPause -> {
                audioPlayer.playOrPause()
            }

            Intent.SkipToNextAudio -> {
                audioPlayer.next()
            }

            Intent.SkipToPreviousAudio -> {
                audioPlayer.previous()
            }

            is Intent.ChangeUserPosition -> {
                dispatch(
                    Message.UpdateUserPosition(
                        intent.position,
                        formatter.format(intent.position),
                    ),
                )
            }

            Intent.ChangeUserPositionFinished -> {
                audioPlayer.seekTo(getState().userPosition)
            }

            Intent.Download -> {
                val chapter = requireNotNull(getState().currentChapter) { "Can't be null" }
                audioPlayer.download(chapter)
            }

            Intent.Remove -> {
                val audioBook = requireNotNull(getState().currentChapter) { "Can't be null" }
                audioPlayer.removeDownload(audioBook)
            }

            is Intent.Play -> {
                audioPlayer.play(intent.chapter)
            }

            is Intent.UpdateAudioBook -> {
                dispatch(Message.UpdateAudioBook(intent.audioBook))
            }
        }
    }
}
