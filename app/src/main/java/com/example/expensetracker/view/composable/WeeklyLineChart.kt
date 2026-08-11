package com.example.expensetracker.view.composable
//
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate
import kotlin.math.roundToInt

//
//@Composable
//fun WeeklyLineChart(data: Map<LocalDate, Double>) {
//    val points = data.entries.sortedBy { it.key }
//
//    Canvas (
//        modifier = Modifier
//            .fillMaxWidth()
//            .background(color = Color(0xff020617))
//            .padding(16.dp)
//            .height(200.dp)
//
//    ) {
//        if (points.isEmpty()) return@Canvas
//
//        val maxY = points.maxOf { it.value }.coerceAtLeast(1.0)
//        val spaceX = size.width / (points.size - 1).coerceAtLeast(1)
//
//        val path = Path()
//
//        points.forEachIndexed { index, entry ->
//            val x = index * spaceX
//            val y = size.height - (entry.value / maxY * size.height)
//
//            if (index == 0) path.moveTo(x, y.toFloat())
//            else path.lineTo(x, y.toFloat())
//        }
//
//        drawPath(
//            path = path,
//            color = Color(0xFF4CAF50),
//            style = Stroke(width = 4f, cap = StrokeCap.Round),
//        )
//    }
//}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeeklyLineChart(
    data: Map<LocalDate, Double>,
    modifier: Modifier = Modifier
) {
    if (data.isEmpty()) return

    val maxY = data.values.maxOrNull() ?: 1.0
    val points = data.entries.sortedBy { it.key }
    var selectedPoint by remember { mutableStateOf<Pair<LocalDate, Double>?>(null) }

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
        .height(250.dp)
        .background(color = Color(0xff020617))
    ) {

        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .padding( 16.dp)
                .pointerInput(Unit) {
                    detectTapGestures { offset ->
                        val spaceX = size.width / (points.size - 1).coerceAtLeast(1)
                        val index = (offset.x / spaceX).roundToInt()
                            .coerceIn(0, points.lastIndex)
                        selectedPoint = points[index].toPair()
                    }
                }
        ) {
            val spaceX = size.width / (points.size - 1).coerceAtLeast(1)

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

            // ✏️ رسم الخط
            val path = Path()
            points.forEachIndexed { index, pair ->
                val x = index * spaceX
                val y = size.height - (pair.value.toFloat() / maxY.toFloat() * size.height)
                if (index == 0) path.moveTo(x, y) else path.lineTo(x, y)
            }

            drawPath(
                path = path,
                color = Color(0xFF1B981F),
                style = Stroke(width = 4f, cap = StrokeCap.Round)
            )

            // 🔴 رسم النقاط
            points.forEachIndexed { index, pair ->
                val x = index * spaceX
                val y = size.height - (pair.value.toFloat() / maxY.toFloat() * size.height)
                drawCircle(
                    color = if (selectedPoint?.first == pair.key) Color.Yellow else Color.White,
                    radius = if (selectedPoint?.first == pair.key) 8f else 6f,
                    center = Offset(x, y)
                )
            }
        }

        // 🏷️ أسماء الأيام تحت الرسم
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            points.forEach { entry ->
                Text(
                    text = entry.key.dayOfWeek.name.take(3),
                    color = Color(0xFFCBD5E1),
                    fontSize = 12.sp
                )
            }
        }

        // 🧾 القيمة المختارة
        selectedPoint?.let { (date, value) ->
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "${date.dayOfWeek.name} → $value",
                color = Color.White,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }

//        drawPath(
//            path = path,
//            color = Color(0xFF1B981F),
//            style = Stroke(width = 4f, cap = StrokeCap.Round)
//        )



}
