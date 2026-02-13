package me.weishu.kernelsu.ui.component.material3

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Material 3 AlertDialog wrapper
 * Replaces Miuix's Dialog with standard Material 3 implementation
 */
@Composable
fun M3AlertDialog(
    onDismissRequest: () -> Unit,
    title: String? = null,
    text: String? = null,
    confirmButton: @Composable () -> Unit,
    dismissButton: @Composable (() -> Unit)? = null
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = title?.let { t ->
            {
                Text(
                    text = t,
                    modifier = Modifier.padding(16.dp)
                )
            }
        },
        text = text?.let { t ->
            {
                Text(
                    text = t,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        },
        confirmButton = confirmButton,
        dismissButton = dismissButton?.let { btn ->
            {
                dismissButton()
            }
        }
    )
}

/**
 * Material 3 TextButton for dialog actions
 */
@Composable
fun M3TextButton(
    text: String,
    onClick: () -> Unit
) {
    TextButton(
        onClick = onClick,
        colors = ButtonDefaults.textButtonColors()
    ) {
        Text(text)
    }
}
