package kaa.alisherbu.listbook.feature.home.store

import com.arkivanov.mvikotlin.core.store.Reducer
import javax.inject.Inject

internal class HomeReducer @Inject constructor() : Reducer<HomeState, Message> {
    override fun HomeState.reduce(msg: Message): HomeState {
        return when (msg) {
            is Message.UpdateAudioBooks -> {
                copy(audioBooks = msg.audioBooks)
            }
        }
    }
}
