package kaa.alisherbu.listbook.common.signup.component

import com.arkivanov.decompose.ComponentContext

class SuccessDialogComponent(
    private val componentContext: ComponentContext,
    val message: String,
    private val onDismissed: () -> Unit
) : ComponentContext by componentContext {
    fun dismiss() {
        onDismissed()
    }
}