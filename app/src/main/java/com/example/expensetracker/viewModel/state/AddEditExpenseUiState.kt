package com.example.expensetracker.viewModel.state

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.expensetracker.domain.model.ExpenseCategory
import com.example.expensetracker.viewModel.model.ExpenseUi
import java.time.LocalDate


data class AddEditExpenseUiState @RequiresApi(Build.VERSION_CODES.O) constructor(

    val titleValue: String = "",
    val amountValue: String = "",
    val descriptionValue: String = "",
    val selectedDate: String = "",
    @RequiresApi(Build.VERSION_CODES.O)
    val selectedLocalDate: LocalDate = LocalDate.now(),
    val mode: ScreenMode = ScreenMode.ADD,
    val selectedCategory: ExpenseCategory = ExpenseCategory.FOOD,
    val showDateDialog: Boolean = false,
    val showDeleteDialog: Boolean = false,
    val expenseToDelete: ExpenseUi? = null,
    val isSaveEnabled: Boolean = false,
    )

sealed class ScreenMode {
    object ADD : ScreenMode()
    data class EDIT(val expenseId: Long) : ScreenMode()
}


