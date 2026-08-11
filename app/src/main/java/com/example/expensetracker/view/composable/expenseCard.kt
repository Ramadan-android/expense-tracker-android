package com.example.expensetracker.view.composable

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.expensetracker.viewModel.model.ExpenseUi



@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ExpenseCard(
    expense: ExpenseUi,
    onClickCard: (ExpenseUi) -> Unit,
    modifier: Modifier = Modifier
){

    Card (
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = { onClickCard(expense) })
            .padding(10.dp)
            .shadow(
                elevation = 20.dp,
                shape = RoundedCornerShape(50.dp),
                clip = false
            )
            .background(
                brush = Brush.linearGradient(
                    listOf(
                        Color(0xFF0B1C2D),
                        Color(0xFF102A43),
                        Color(0xFF1E3A8A)
                    ),
                    start = Offset(0f, 0f),
                    end = Offset(900f, 200f)

                ),
                shape = RoundedCornerShape(15.dp)

            ),
//        elevation = CardDefaults.cardElevation(
//            defaultElevation = 100.dp
//        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
    ) {
        Column (
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 10.dp)
        ) {
            Row (
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                TextFs26(expense.title, modifier = Modifier
                    .weight(1f),
                    color = Color(0xff38BDF8)
                )
                TextFs26("---->",
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 4.dp),
                    color = Color(0xff34D399)
                    )
                TextFs26(expense.amount.toString(), color = Color(0xFFF5FF74))
            }
            TextAlignFsDf16(text = expense.category.toString(), align = TextAlign.Center, color = Color(
                0xD5FAE860
            )
            )
            TextAlignFsDf16(expense.date
                .toString(), align = TextAlign.Right,color = Color(0xff9CA3AF))
            TextAlignFsDf16(text = expense.description, align = TextAlign.Left, color = Color(0xff9CA3AF))

        }
    }
}