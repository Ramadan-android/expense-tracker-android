package com.example.expensetracker.viewModel.state

import com.example.expensetracker.domain.model.ExpenseCategory
import java.time.LocalDate


data class ExpenseListUiState(
    val searchQuery: String = "",
    val selectedCategory: ExpenseCategory = ExpenseCategory.ALL,
    val showFilterDialog: Boolean = false,
    val filterBy: String = "",
    val totalDay: Double = 0.0,
    val totalWeek: Double = 0.0,
    val totalMonth: Double = 0.0,

)


