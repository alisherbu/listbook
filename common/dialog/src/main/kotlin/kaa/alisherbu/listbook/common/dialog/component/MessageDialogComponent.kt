package kaa.alisherbu.listbook.common.dialog.component

import com.arkivanov.decompose.ComponentContext

class MessageDialogComponent(
    private val componentContext: ComponentContext,
    val message: String,
    private val onDismissed: () -> Unit,
) : ComponentContext by componentContext {


    fun onDismissClicked() {
        onDismissed()
    }
}