package kaa.alisherbu.listbook.feature.main.store

import com.arkivanov.mvikotlin.core.store.Store
import kaa.alisherbu.listbook.core.shared.model.AudioBook

internal sealed interface Intent {
    class Play(val audioBook: AudioBook) : Intent
    object PlayOrPause : Intent
}

internal sealed interface Label
internal sealed interface Action
internal sealed interface Message {
    class PlayOrPause(val isPlaying: Boolean) : Message
}

internal interface MainStore : Store<Intent, MainState, Label>
