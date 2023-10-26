package kaa.alisherbu.listbook.feature.main.store

import com.arkivanov.mvikotlin.core.store.Reducer
import javax.inject.Inject

internal class MainReducer @Inject constructor() : Reducer<MainState, Message> {
    override fun MainState.reduce(msg: Message): MainState {
        return when (msg) {
            is Message.PlayOrPause -> {
                copy(isPlaying = msg.isPlaying)
            }

            is Message.UpdateAudioBook -> {
                copy(currentAudioBook = msg.audioBook)
            }
        }
    }
}
