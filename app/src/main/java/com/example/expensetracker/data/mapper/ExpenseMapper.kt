package com.example.expensetracker.data.mapper

import com.example.expensetracker.data.database.expense.Expense as ExpenseEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.example.expensetracker.domain.model.Expense
import javax.inject.Inject

class ExpenseMapper @Inject constructor() {

    fun toDomain(entity: ExpenseEntity): Expense =
        Expense(
            id = entity.id,
            title = entity.title,
            amount = entity.amount,
            category = entity.category,
            date = entity.date,
            description = entity.description
        )

    fun toDomainFlow(flow: Flow<ExpenseEntity>): Flow<Expense> =
        flow.map { toDomain(it) }

    fun toEntity(domain: Expense): ExpenseEntity =
        ExpenseEntity(
            id = domain.id,
            title = domain.title,
            amount = domain.amount,
            category = domain.category,
            date = domain.date,
            description = domain.description
        )


    fun toDomainList(flow: Flow<List<ExpenseEntity>>): Flow<List<Expense>> =
        flow.map { list -> list.map { toDomain(it) } }
}
