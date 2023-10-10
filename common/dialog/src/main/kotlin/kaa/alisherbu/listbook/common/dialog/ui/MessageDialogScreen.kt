package kaa.alisherbu.listbook.common.dialog.ui

import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kaa.alisherbu.listbook.common.dialog.component.MessageDialogComponent

@Composable
fun DialogScreen(dialogComponent: MessageDialogComponent) {
    AlertDialog(
        onDismissRequest = {
            dialogComponent.onDismissClicked()
        },
        text = {
            Text(text = dialogComponent.message)
        },
        confirmButton = {
            TextButton(
                onClick = {
                    dialogComponent.onDismissClicked()
                }
            ) {
                Text("Dismiss")
            }
        },
        modifier = Modifier.width(300.dp),
    )
}