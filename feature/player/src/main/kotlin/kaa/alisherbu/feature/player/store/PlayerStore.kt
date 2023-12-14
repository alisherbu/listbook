package kaa.alisherbu.feature.player.store

import com.arkivanov.mvikotlin.core.store.Store
import kaa.alisherbu.listbook.core.shared.model.AudioBook
import kaa.alisherbu.listbook.core.shared.model.Chapter

internal sealed interface Intent {
    data object SkipToPreviousAudio : Intent
    data object SkipToNextAudio : Intent
    data object PlayOrPause : Intent
    class ChangeUserPosition(val position: Long) : Intent
    data object ChangeUserPositionFinished : Intent
    data object Download : Intent
    data object Remove : Intent
    class Play(val chapter: Chapter) : Intent
    class UpdateAudioBook(val audioBook: AudioBook) : Intent
}

internal sealed interface Action {
    data object Init : Action
}

internal sealed interface Message {
    class UpdateChapter(val chapter: Chapter) : Message
    class PlayOrPause(val isPlaying: Boolean) : Message
    class UpdateCurrentPosition(val position: Long, val positionText: String) : Message
    class UpdateUserPosition(val position: Long, val positionText: String) : Message
    class UpdateDuration(val duration: Long, val durationText: String) : Message
    class UpdateAudioBook(val audioBook: AudioBook) : Message
}

internal sealed interface Label

internal interface PlayerStore : Store<Intent, PlayerState, Label>
