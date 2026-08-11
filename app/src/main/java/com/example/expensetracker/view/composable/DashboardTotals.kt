package com.example.expensetracker.view.composable

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
            TextAlignFsf16("Today: ${dashboardState.totalDay}", color = Color(0xffffffff), modifier = Modifier.weight(1F))
            TextAlignFsf16("Week: ${dashboardState.totalWeek}",  color = Color(0xffffffff), modifier = Modifier.weight(1F))
            TextAlignFsf16("Month: ${dashboardState.totalMonth}",  color = Color(0xffffffff), modifier = Modifier.weight(1F))
        }
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth()
        ) {
            TextAlignFsf16("Salery: ${dashboardState.salery}", color = Color(0xffffffff), modifier = Modifier.weight(1F))
            TextAlignFsf16("Balanse: ${dashboardState.balance}", color = Color(0xffffffff), modifier = Modifier.weight(1F))
        }
    }
}