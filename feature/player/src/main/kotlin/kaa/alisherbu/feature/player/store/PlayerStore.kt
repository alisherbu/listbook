package kaa.alisherbu.feature.player.store

import com.arkivanov.mvikotlin.core.store.Store

internal sealed interface Intent
internal sealed interface Action
internal sealed interface Message
internal sealed interface Label

internal interface PlayerStore : Store<Intent, PlayerState, Label>