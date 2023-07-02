package kaa.alisherbu.listbook.common.auth.store

import com.arkivanov.mvikotlin.core.store.Store

internal sealed interface Intent

internal data class AuthState(val a: String = "")

internal sealed interface Label

internal sealed interface Message

internal interface AuthStore : Store<Intent, AuthState, Label>
