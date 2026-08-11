package com.example.expensetracker.data.DataRepositores

import com.example.expensetracker.domain.model.Expense
import com.example.expensetracker.data.database.expense.ExpenseDao
import com.example.expensetracker.data.mapper.ExpenseMapper
import com.example.expensetracker.domain.repository.DataRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DatabaseRepositoryImpl @Inject constructor(
    private val expenseDao: ExpenseDao,
    private val mapper: ExpenseMapper
) : DataRepository {
    override fun get(): Flow<List<Expense>> {
        return mapper.toDomainList(expenseDao.getAllExpenses())
    }

    override fun getExpenseById(id: Long): Flow<Expense?> {
        return mapper.toDomainFlow(expenseDao.getExpenseById(id))
    }

    override suspend fun add(expense: Expense) = expenseDao.add(mapper.toEntity(expense))


    override suspend fun update(expense: Expense) = expenseDao.update(mapper.toEntity(expense))

    override suspend fun delete(expense: Expense) = expenseDao.delete(mapper.toEntity(expense))


}