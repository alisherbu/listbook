package kaa.alisherbu.listbook.feature.main.store

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kaa.alisherbu.listbook.core.shared.model.AudioBook
import kaa.alisherbu.listbook.feature.main.domain.usecase.GetChaptersByBookIdFlowUseCase
import kaa.alisherbu.service.player.AudioPlayer
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class MainExecutor @Inject constructor(
    private val audioPlayer: AudioPlayer,
    private val getChaptersByBookIdFlow: GetChaptersByBookIdFlowUseCase
) : CoroutineExecutor<Intent, Action, MainState, Message, Label>() {

    init {
        audioPlayer.isPlaying.onEach {
            dispatch(Message.PlayOrPause(it))
        }.launchIn(scope)

        audioPlayer.currentChapter.onEach {
            dispatch(Message.UpdateChapter(it))
        }.launchIn(scope)
    }

    override fun executeIntent(intent: Intent, getState: () -> MainState) {
        val state = getState()
        when (intent) {
            is Intent.Play -> scope.launch {
                dispatch(Message.UpdateAudioBook(intent.audioBook))
                subscribeChaptersFlow(intent.audioBook)
            }

            Intent.PlayOrPause -> {
                audioPlayer.playOrPause()
            }

            Intent.OpenPlayer -> {
                val audioBook = state.currentAudioBook ?: error("AudioBook should be null here")
                publish(Label.OpenPlayer(audioBook))
            }
        }
    }

    private fun subscribeChaptersFlow(audioBook: AudioBook) {
        getChaptersByBookIdFlow(audioBook.id).onEach {
            audioPlayer.loadChapters(it)
        }.launchIn(scope)
    }
}
