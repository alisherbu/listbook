package kaa.alisherbu.feature.player.store

import com.arkivanov.mvikotlin.core.store.Reducer
import javax.inject.Inject

internal class PlayerReducer @Inject constructor() : Reducer<PlayerState, Message> {
    override fun PlayerState.reduce(msg: Message): PlayerState {
        return when (msg) {
            is Message.SetAudioBook -> {
                copy(title = msg.title)
            }

            is Message.PlayOrPause -> {
                copy(isPlaying = msg.isPlaying)
            }
        }
    }
}