package com.example.expensetracker.view.composable

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ExpenseTextField(
    value: String,
    onValueChange: (String)-> Unit,
    hint: String,
    showSearchIcon: Boolean = false,
    horizontalPadding: Dp = 36.dp,
    maxLines: Int = 1,
    singleLine: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text
){
    TextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = TextStyle(color = Color(0xffffffff)),
        placeholder = { Text(hint, color = Color(0xFFFBFF8A)) },
        modifier = Modifier
            .padding(horizontal = horizontalPadding, vertical = 5.dp),
        maxLines = maxLines,
        singleLine = singleLine,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        trailingIcon = if (showSearchIcon) {
            {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = "search icon",
                    tint = Color(0xffE5E7EB)
                    )
            }
            } else null,
        shape = RoundedCornerShape(16.dp),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedContainerColor = Color(0xFF003A6F),
            unfocusedContainerColor = Color(0xFF102A43),
        )
    )
}