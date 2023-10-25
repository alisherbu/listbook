package kaa.alisherbu.feature.player.store

import com.arkivanov.mvikotlin.core.store.Store
import kaa.alisherbu.listbook.core.shared.model.AudioBook

internal sealed interface Intent {
    object SkipToPreviousAudio : Intent
    object SkipToNextAudio : Intent
    object PlayOrPause : Intent
    class ChangeUserPosition(val position: Long) : Intent
    object ChangeUserPositionFinished : Intent
    object Download : Intent
}

internal sealed interface Action
internal sealed interface Message {
    class UpdateAudioBook(val audioBook: AudioBook) : Message
    class PlayOrPause(val isPlaying: Boolean) : Message
    class UpdateCurrentPosition(val position: Long, val positionText: String) : Message
    class UpdateUserPosition(val position: Long, val positionText: String) : Message
    class UpdateDuration(val duration: Long, val durationText: String) : Message
}

internal sealed interface Label

internal interface PlayerStore : Store<Intent, PlayerState, Label>