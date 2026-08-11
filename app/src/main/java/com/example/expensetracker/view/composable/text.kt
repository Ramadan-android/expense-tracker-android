package com.example.expensetracker.view.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.TextStyle
@Composable
fun TextFs26(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified ,
    style: TextStyle = LocalTextStyle.current
){
    Text(
        text,
        fontSize = 26.sp,
        modifier = modifier,
        color = color,
        style = style
    )
}

@Composable
fun TextFs26Aline(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified ,
    style: TextStyle = LocalTextStyle.current,
    align: TextAlign
){
    Text(
        text,
        fontSize = 26.sp,
        modifier = modifier,
        color = color,
        style = style,
        textAlign = align
    )
}

@Composable
fun TextAlignFsDf16(text: String, align: TextAlign,fontSize: TextUnit = 16.sp, color: Color = Color.Unspecified){
    Text(text, modifier = Modifier.fillMaxWidth(), fontSize = fontSize, textAlign = align,color = color)

}
@Composable
fun TextAlignFsf16(text: String, modifier: Modifier = Modifier, fontSize: TextUnit = 16.sp, color: Color = Color.Unspecified){
    Text(text, modifier = modifier, fontSize = fontSize,color = color)

}