package com.example.notaya.view.composable.dialog

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun DeleteAlertDialog(
    onConfirm:() -> Unit,
    onDismiss: () -> Unit,
    expenseTitle: String,
){
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Delete Expense $expenseTitle") },
        text = { Text("Are you sure?") },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Delete", color = Color.Red)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Dismiss", color = Color.DarkGray)
            }
        }
    )

}