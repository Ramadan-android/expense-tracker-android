package com.example.expensetracker.view.composable

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CoustemButton(
    text: String,
    enabled: Boolean,
    onClickSave: () -> Unit,

    ) {
    Button(
        onClick = onClickSave,
        modifier = Modifier
            .size(300.dp,55.dp),
        shape = RoundedCornerShape(18.dp),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xff38BDF8)
        )
    ) {
        Text(text, fontSize = 20.sp)
    }
}