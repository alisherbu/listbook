package kaa.alisherbu.feature.player.store

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import javax.inject.Inject

internal class PlayerExecutor @Inject constructor():CoroutineExecutor<Intent, Action, PlayerState, Message, Label>() {
}