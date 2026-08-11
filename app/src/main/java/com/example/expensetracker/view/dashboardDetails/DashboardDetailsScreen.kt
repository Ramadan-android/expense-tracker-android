package com.example.expensetracker.view.dashboardDetails

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.expensetracker.view.composable.MonthlyBarChart
import com.example.expensetracker.view.composable.WeeklyLineChart
import com.example.expensetracker.viewModel.DashboardViewModel
import com.example.expensetracker.viewModel.state.DashboardUiState

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DashboardDetailsScreen(
    viewModel: DashboardViewModel = hiltViewModel()
    ){
    val state by viewModel.state.collectAsState()
    Scaffold(
        containerColor = Color(0xffffffff)

    ) {
        DashboardDetailsContent(
            state = state,
            modifier = Modifier.padding(it)
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun DashboardDetailsContent(
    state: DashboardUiState,
    modifier: Modifier = Modifier
){

    Column (
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        Text("Weekly Expenses", style = MaterialTheme.typography.titleMedium)
        WeeklyLineChart(state.weekChart)

        Text("Monthly Expenses", style = MaterialTheme.typography.titleMedium)
        MonthlyBarChart(state.monthChart)
    }
}