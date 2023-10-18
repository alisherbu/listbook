package kaa.alisherbu.listbook.common.signup.ui

import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kaa.alisherbu.listbook.common.signup.component.SuccessDialogComponent

@Composable
internal fun SuccessDialogScreen(dialogComponent: SuccessDialogComponent) {
    AlertDialog(
        onDismissRequest = dialogComponent::dismiss,
        text = {
            Text(text = dialogComponent.message)
        },
        confirmButton = {
            TextButton(
                onClick = dialogComponent::dismiss
            ) {
                Text("Ok")
            }
        },
        modifier = Modifier.width(300.dp),
    )
}