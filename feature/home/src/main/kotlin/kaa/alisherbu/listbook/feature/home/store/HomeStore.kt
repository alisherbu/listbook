package kaa.alisherbu.listbook.feature.home.store

import com.arkivanov.mvikotlin.core.store.Store
import kaa.alisherbu.listbook.core.shared.model.AudioBook

internal sealed interface Intent
internal sealed interface Action {
    object LoadAudioBooks : Action
}

internal sealed interface Label
internal sealed interface Message {
    class UpdateAudioBooks(val audioBooks: List<AudioBook>) : Message
}

internal interface HomeStore : Store<Intent, HomeState, Label>