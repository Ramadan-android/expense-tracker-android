package com.example.expensetracker.viewModel.model

import com.example.expensetracker.domain.model.ExpenseCategory
import java.time.LocalDate

data class ExpenseUi(
    val id: Long,
    val title: String,
    val amount: Double,
    val category: ExpenseCategory,
    val date: LocalDate,
    val description: String

)
