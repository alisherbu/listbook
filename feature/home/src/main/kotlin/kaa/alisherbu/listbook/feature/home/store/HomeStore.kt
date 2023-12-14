package kaa.alisherbu.listbook.feature.home.store

import com.arkivanov.mvikotlin.core.store.Store
import kaa.alisherbu.listbook.core.shared.model.AudioBook
import kaa.alisherbu.listbook.core.shared.model.Category

internal sealed interface Intent {
    data object Refresh : Intent
}

internal sealed interface Action {
    data object Init : Action
}

internal sealed interface Label
internal sealed interface Message {
    class UpdateCategories(val categories: List<Category>) : Message
    class UpdateAudioBooks(val audioBooks: List<AudioBook>) : Message
    class UpdateRefresh(val isRefreshing: Boolean) : Message
}

internal interface HomeStore : Store<Intent, HomeState, Label>
