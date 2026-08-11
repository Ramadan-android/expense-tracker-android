package com.example.expensetracker.viewModel.state

import java.time.LocalDate

data class DashboardUiState(
    val totalDay: Double = 0.0,
    val totalWeek: Double = 0.0,
    val totalMonth: Double = 0.0,
    val salary: Double = 0.0,
    val balance: Double = 0.0,
    val weekChart: Map<LocalDate, Double> = emptyMap(),
    val monthChart: Map<LocalDate, Double> = emptyMap(),

    )
