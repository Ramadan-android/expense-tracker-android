package com.example.trainingapp.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp

@Composable
fun FilterDropdown(
    onClickDismissFilter: ()-> Unit,
    onClickConfFilter: (String)-> Unit
){
    DropdownMenu(
        expanded = true,
        onDismissRequest = onClickDismissFilter,
        offset = DpOffset((-20).dp,335.dp),
        border = BorderStroke(2.dp, color = Color(0xFFE9E9E9f)),
        shape = RoundedCornerShape(16.dp),
        containerColor = Color(0xFFE9E9E9),

        ) {
        DropdownMenuItem(
            text = {Text("Title", color = Color.Black)},
            onClick = { onClickConfFilter("title") },

            )
        DropdownMenuItem(
            text = {Text("Amount", color = Color.Black)},
            onClick = { onClickConfFilter("amount") },
        )
        DropdownMenuItem(
        text = {Text("Date", color = Color.Black)},
        onClick = { onClickConfFilter("date") },
    )
        DropdownMenuItem(
            text = {Text("Reset", color = Color.Black)},
            onClick = { onClickConfFilter("Reset") },
        )
    }
}