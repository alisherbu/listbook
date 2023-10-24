package kaa.alisherbu.feature.player.store

import com.arkivanov.mvikotlin.core.store.Reducer
import javax.inject.Inject

internal class PlayerReducer @Inject constructor() : Reducer<PlayerState, Message> {
    override fun PlayerState.reduce(msg: Message): PlayerState {
        return when (msg) {
            is Message.UpdateAudioBook -> {
                copy(currentAudioBook = msg.audioBook)
            }

            is Message.PlayOrPause -> {
                copy(isPlaying = msg.isPlaying)
            }
        }
    }
}