package com.example.expensetracker.domain.repository


import com.example.expensetracker.domain.model.Expense
import kotlinx.coroutines.flow.Flow

interface DataRepository {

    fun get(): Flow<List<Expense>>
    fun getExpenseById(id: Long): Flow<Expense?>

    suspend fun add(expense: Expense)

    suspend fun update(expense: Expense)

    suspend fun delete(expense: Expense)
}