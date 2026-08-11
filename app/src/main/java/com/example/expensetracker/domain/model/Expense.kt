package com.example.expensetracker.domain.model


data class Expense(
    val id: Long,
    val title: String,
    val amount: Double,
    val category: ExpenseCategory,
    val date: Long,
    val description: String
)


enum class ExpenseCategory {
    ALL,
    FOOD,
    HEALTH,
    TRANSPORTATION,
    ENTERTAINMENT,
}
