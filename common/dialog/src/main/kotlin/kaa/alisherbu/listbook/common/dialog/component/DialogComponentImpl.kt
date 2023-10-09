package kaa.alisherbu.listbook.common.dialog.component

import com.arkivanov.decompose.ComponentContext
import kaa.alisherbu.listbook.common.dialog.component.DialogComponent

class DialogComponentImpl(
    private val componentContext: ComponentContext,
    override val message: String,
    private val onDismissed: () -> Unit,
) : DialogComponent, ComponentContext by componentContext {


    override fun onDismissClicked() {
        onDismissed()
    }
}