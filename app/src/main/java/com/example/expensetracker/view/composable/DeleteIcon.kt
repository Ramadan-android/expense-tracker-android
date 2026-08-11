package com.example.expensetracker.view.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DeleteIcon(
    onClickIcon: ()-> Unit
){
    Icon(
        imageVector = Icons.Default.Delete,
        contentDescription = "Delete",
        tint = Color.Red,
        modifier = Modifier
            .size(40.dp)
            .clickable(onClick = onClickIcon)
            .padding(end = 10.dp)
    )

}