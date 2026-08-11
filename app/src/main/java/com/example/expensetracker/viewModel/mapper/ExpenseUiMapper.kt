package com.example.expensetracker.viewModel.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.expensetracker.domain.model.Expense
import com.example.expensetracker.viewModel.model.ExpenseUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.Instant
import java.time.ZoneId
import javax.inject.Inject

class ExpenseUiMapper @Inject constructor(){
    @RequiresApi(Build.VERSION_CODES.O)
    fun toUi(domain: Expense): ExpenseUi =
        ExpenseUi(
            id = domain.id,
            title = domain.title,
            amount = domain.amount,
            category = domain.category,
            date = Instant.ofEpochMilli(domain.date)
                .atZone(ZoneId.systemDefault())
                .toLocalDate(),
            description = domain.description
        )
    @RequiresApi(Build.VERSION_CODES.O)
    fun toDomain(ui: ExpenseUi): Expense =
        Expense(
            id = ui.id,
            title = ui.title,
            amount = ui.amount,
            category = ui.category,
            date = ui.date
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli(),
            description = ui.description
        )
    @RequiresApi(Build.VERSION_CODES.O)
    fun toUiList(domain: Flow<List<Expense>>): Flow<List<ExpenseUi>> =


        domain.map { domainExpenses -> domainExpenses.map { toUi(it) } }

}