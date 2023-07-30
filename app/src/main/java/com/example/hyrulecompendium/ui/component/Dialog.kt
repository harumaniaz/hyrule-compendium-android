package com.example.hyrulecompendium.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hyrulecompendium.ui.theme.HcTheme
import com.example.hyrulecompendium.ui.theme.PrimaryTextColor

@Composable
fun SimpleAlertDialog(
    title: String? = null,
    message: String? = null,
    confirmButtonText: String,
    onConfirmClick: () -> Unit,
    dismissButtonText: String? = null,
    onDismissClick: () -> Unit = {},
    isCancelable: Boolean = true
) {
    AlertDialog(
        onDismissRequest = {
            if (isCancelable) {
                onDismissClick()
            }
        },
        confirmButton = {
            TextButton(onClick = onConfirmClick) {
                Text(text = confirmButtonText)
            }
        },
        modifier = Modifier.border(
            border = BorderStroke(1.dp, PrimaryTextColor),
            shape = ShapeDefaults.Medium
        ),
        shape = ShapeDefaults.Medium,
        dismissButton = {
            dismissButtonText?.let {
                TextButton(onClick = onDismissClick) {
                    Text(text = it)
                }
            }
        },
        title = {
            title?.let {
                Text(text = it)
            }
        },
        text = {
            message?.let {
                Text(text = it)
            }
        }
    )
}

@Preview
@Composable
fun SimpleAlertDialogPreview() {
    HcTheme {
        SimpleAlertDialog(
            title = "Dialog Title",
            message = "This is dialog message. This is dialog message. This is dialog message.",
            confirmButtonText = "OK",
            onConfirmClick = { },
            dismissButtonText = "Cancel"
        )
    }
}