package com.example.expensetracker.data.database.expense

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.expensetracker.domain.model.ExpenseCategory

@Entity(tableName = "expenses")
data class Expense(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val title: String,
    val amount: Double,
    val category: ExpenseCategory,
    val date: Long,
    val description: String
)
