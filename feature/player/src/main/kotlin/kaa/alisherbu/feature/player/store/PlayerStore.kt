package kaa.alisherbu.feature.player.store

import com.arkivanov.mvikotlin.core.store.Store
import kaa.alisherbu.listbook.core.shared.model.AudioBook

internal sealed interface Intent {
    class Initialize(val audioBook: AudioBook) : Intent
    object SkipToPreviousAudio : Intent
    object SkipToNextAudio : Intent
    object PlayOrPause : Intent
}

internal sealed interface Action
internal sealed interface Message {
    class UpdateAudioBook(val audioBook: AudioBook) : Message
    class PlayOrPause(val isPlaying: Boolean) : Message
}

internal sealed interface Label

internal interface PlayerStore : Store<Intent, PlayerState, Label>