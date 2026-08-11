package com.example.expensetracker.data.database.user

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = false) val id: Long = 1,
    val name: String,
    val monthlyBudget: Double,
)
