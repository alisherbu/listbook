package kaa.alisherbu.listbook.feature.main.store

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kaa.alisherbu.listbook.feature.main.domain.usecase.GetChaptersByBookIdUseCase
import kaa.alisherbu.service.player.AudioPlayer
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class MainExecutor @Inject constructor(
    private val audioPlayer: AudioPlayer,
    private val getChaptersByBookId: GetChaptersByBookIdUseCase
) : CoroutineExecutor<Intent, Action, MainState, Message, Label>() {

    init {
        audioPlayer.isPlaying.onEach {
            dispatch(Message.PlayOrPause(it))
        }.launchIn(scope)

        audioPlayer.currentAudioBook.onEach {
            dispatch(Message.UpdateAudioBook(it))
        }.launchIn(scope)
    }

    override fun executeIntent(intent: Intent, getState: () -> MainState) {
        when (intent) {
            is Intent.Play -> scope.launch {
                val chapters = getChaptersByBookId(intent.audioBook.id)
                audioPlayer.loadAudioBooks(chapters)
            }

            Intent.PlayOrPause -> {
                audioPlayer.playOrPause()
            }
        }
    }
}
