package com.example.expensetracker.view.composable

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.expensetracker.viewModel.state.DashboardUiState

@SuppressLint("DefaultLocale")
@Composable
fun DashboardTotals(
    dashboardState: DashboardUiState,
    onClickDetails: () -> Unit = {} ,
){
    Column(
        modifier = Modifier.fillMaxWidth()
            .clickable(onClick = onClickDetails),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            TextAlignFsf16("Today: ${String.format("%.2f", dashboardState.totalDay)}", color = Color(0xffffffff), modifier = Modifier.weight(1F))
            TextAlignFsf16("Week: ${String.format("%.2f", dashboardState.totalWeek)}",  color = Color(0xffffffff), modifier = Modifier.weight(1F))
            TextAlignFsf16("Month: ${String.format("%.2f", dashboardState.totalMonth)}",  color = Color(0xffffffff), modifier = Modifier.weight(1F))
        }
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth()
        ) {
            TextAlignFsf16("Salary: ${String.format("%.2f", dashboardState.salary)}", color = Color(0xffffffff), modifier = Modifier.weight(1F))
            TextAlignFsf16("Balance: ${String.format("%.2f", dashboardState.balance)}", color = Color(0xffffffff), modifier = Modifier.weight(1F))
        }
    }
}