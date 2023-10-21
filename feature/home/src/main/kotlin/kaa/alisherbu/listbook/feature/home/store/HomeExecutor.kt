package kaa.alisherbu.listbook.feature.home.store

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import javax.inject.Inject

internal class HomeExecutor @Inject constructor(

) : CoroutineExecutor<Intent, Action, HomeState, Message, Label>() {
}