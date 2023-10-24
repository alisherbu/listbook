package kaa.alisherbu.feature.player.store

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kaa.alisherbu.listbook.core.shared.player.AudioPlayer
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

internal class PlayerExecutor @Inject constructor(
    private val audioPlayer: AudioPlayer
) : CoroutineExecutor<Intent, Action, PlayerState, Message, Label>() {
    private val formatter = SimpleDateFormat("mm:ss", Locale.getDefault())

    init {
        audioPlayer.isPlaying.onEach {
            dispatch(Message.PlayOrPause(it))
        }.launchIn(scope)

        audioPlayer.currentAudioBook.onEach {
            checkNotNull(it) { "AudioBook shouldn't be null in the player" }
            dispatch(Message.UpdateAudioBook(it))
        }.launchIn(scope)

        audioPlayer.currentPosition.onEach {
            dispatch(
                Message.UpdateCurrentPosition(
                    position = it,
                    positionText = formatter.format(it)
                )
            )
        }.launchIn(scope)

        audioPlayer.duration.onEach {
            if (it < 0) return@onEach
            dispatch(
                Message.UpdateDuration(
                    duration = it,
                    durationText = formatter.format(it)
                )
            )
        }.launchIn(scope)
    }

    override fun executeIntent(intent: Intent, getState: () -> PlayerState) {
        when (intent) {
            is Intent.Initialize -> {

            }

            Intent.PlayOrPause -> {
                audioPlayer.playOrPause()
            }

            Intent.SkipToNextAudio -> {
                audioPlayer.next()
            }

            Intent.SkipToPreviousAudio -> {
                audioPlayer.previous()
            }
        }
    }
}