package com.example.expensetracker.view.composable

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MonthlyBarChart(
    data: Map<LocalDate, Double>,
    modifier: Modifier = Modifier
) {
    if (data.isEmpty()) return

    val maxY = data.values.maxOrNull() ?: 1.0
    val bars = data.entries.sortedBy { it.key }

    var selectedBar by remember { mutableStateOf<Pair<LocalDate, Double>?>(null) }

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .background(color = Color(0xff020617))
            .height(270.dp)
    ) {

        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
                .padding(horizontal = 16.dp)
                .pointerInput(Unit) {
                    detectTapGestures { offset ->
                        val barWidth = size.width / bars.size
                        val index = (offset.x / barWidth).toInt()
                            .coerceIn(0, bars.lastIndex)
                        selectedBar = bars[index].toPair()
                    }
                }
        ) {
            val barWidth = size.width / bars.size

            // 🔹 خطوط الشبكة
            val gridColor = Color(0x33FFFFFF)
            repeat(4) { i ->
                val y = size.height * (i / 3f)
                drawLine(
                    color = gridColor,
                    start = Offset(0f, y),
                    end = Offset(size.width, y),
                    strokeWidth = 1f
                )
            }

            // 🟦 رسم الأعمدة
            bars.forEachIndexed { index, entry ->
                val barHeight = (entry.value.toFloat() / maxY.toFloat()) * size.height
                val x = index * barWidth
                val y = size.height - barHeight

                drawRoundRect(
                    color = if (selectedBar?.first == entry.key)
                        Color.Yellow
                    else
                        Color(0xFF1B981F),
                    topLeft = Offset(x + 6f, y),
                    size = Size(barWidth - 12f, barHeight),
                    cornerRadius = CornerRadius(12f, 12f)
                )
            }
        }

        // 🏷️ تواريخ الأيام أسفل الأعمدة
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            bars.forEach { entry ->
                Text(
                    text = entry.key.dayOfMonth.toString(),
                    color = Color(0xFFCBD5E1),
                    fontSize = 10.sp
                )
            }
        }

        // 🧾 القيمة المختارة
        selectedBar?.let { (date, value) ->
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "${date.dayOfMonth}/${date.monthValue} → $value",
                color = Color.White,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}
