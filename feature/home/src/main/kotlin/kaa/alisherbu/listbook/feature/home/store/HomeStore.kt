package kaa.alisherbu.listbook.feature.home.store

import com.arkivanov.mvikotlin.core.store.Store

internal sealed interface Intent
internal sealed interface Action
internal sealed interface Label
internal sealed interface Message

internal interface HomeStore : Store<Intent, HomeState, Label>