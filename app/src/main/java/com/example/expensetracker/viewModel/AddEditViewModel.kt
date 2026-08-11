package com.example.expensetracker.viewModel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.domain.model.ExpenseCategory
import com.example.expensetracker.domain.repository.DataRepository
import com.example.expensetracker.route.Routes
import com.example.expensetracker.viewModel.mapper.ExpenseUiMapper
import com.example.expensetracker.viewModel.model.ExpenseUi
import com.example.expensetracker.viewModel.state.AddEditExpenseUiState
import com.example.expensetracker.viewModel.state.ScreenMode
import com.example.expensetracker.viewModel.state.UiEvent
import com.example.expensetracker.viewModel.state.snackbarAddMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class AddEditViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: DataRepository,
    private val mapper: ExpenseUiMapper
) : ViewModel() {

    private val _state = MutableStateFlow(AddEditExpenseUiState())
    val state = _state.asStateFlow()
    private val _event = MutableSharedFlow<UiEvent>()
    val event = _event.asSharedFlow()

    private var expenseOld: ExpenseUi? = null

    init {
        val expenseId: Long? = savedStateHandle[Routes.expenseIdArg]
        if (expenseId != null && expenseId != -1L){
            loadExpenseData(expenseId)
        }else{
            _state.update {
                it.copy(
                    mode = ScreenMode.ADD
                )
            }
        }
    }

    private fun loadExpenseData(id: Long) {
        viewModelScope.launch {
            repository.getExpenseById(id).firstOrNull()?.let { entity ->
                val expense = mapper.toUi(entity)
                expenseOld = expense
                _state.update {
                    it.copy(
                        titleValue = expense.title,
                        amountValue = expense.amount.toString(),
                        descriptionValue = expense.description,
                        selectedCategory = expense.category,
                        selectedDate = expense.date.toString(),
                        mode = ScreenMode.EDIT(id),
                        isSaveEnabled = true
                    )
                }
            }
        }
    }

    fun onTitleChange(title: String){
        _state.update {
            it.copy(
                titleValue = title,
                isSaveEnabled = (title.isNotBlank() && title.isNotBlank())

            )
        }
    }
    fun onAmountChange(amount: String){
        _state.update {
            it.copy(
                amountValue = amount,
                isSaveEnabled = (amount.isNotBlank() && amount.isNotBlank())
            )
        }
    }
    fun onDescriptionChange(description: String){
        _state.update {
            it.copy(
                descriptionValue = description
            )
        }
    }

    fun onChangeCategory(category: ExpenseCategory){
        _state.update {
            it.copy(
                selectedCategory = category
            )
        }
    }

    fun onClickDateDialogButton(){
        _state.update {
            it.copy(
                showDateDialog = true
            )
        }
    }
    fun onDismissDateDialog(){
        _state.update {
            it.copy(
                showDateDialog = false
            )
        }
    }

    fun onClickDeleteIcon(){
        _state.update {
            it.copy(
                showDeleteDialog = true,
                expenseToDelete = expenseOld
            )
        }
    }
    fun onConfirmDelete() {
        if (expenseOld == null) {
            viewModelScope.launch {
                _event.emit(UiEvent.ShowSnackBar("Expense not found"))
            }
        }
        _state.update {
            it.copy(showDeleteDialog = false)
            }
        expenseOld?: return
        viewModelScope.launch {
            _event.emit(UiEvent.DeleteExpense(expenseOld!!.id))
        }

    }
    fun onDissmissDelete() {
        _state.update {
            it.copy(
                showDeleteDialog = false,
                expenseToDelete = null
            )
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalMaterial3Api::class)
    fun onClickSetDate(datePickerState: DatePickerState){
        val millis = datePickerState.selectedDateMillis
        millis?: return
            val date = Instant.ofEpochMilli(millis)
                .atZone(ZoneId.systemDefault())
                .toLocalDate()

            _state.update {
                it.copy(
                    selectedDate = date.toString(),
                    selectedLocalDate = date,
                    showDateDialog = false

                )
            }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onClickSave(){
        val currentExpense = _state.value
        val currentEntityExpense = ExpenseUi(
            id = if (currentExpense.mode is ScreenMode.EDIT) currentExpense.mode.expenseId else 0L,
            title = currentExpense.titleValue,
            amount = currentExpense.amountValue.toDouble(),
            category = currentExpense.selectedCategory,
            date = currentExpense.selectedLocalDate,
            description = currentExpense.descriptionValue
        )
        if (currentExpense.mode is ScreenMode.EDIT){
            viewModelScope.launch {
                val ex = repository.getExpenseById(currentExpense.mode.expenseId).first()
                ex?: return@launch
                val oldExpense = mapper.toUi(ex)

                if (
                    oldExpense.title == currentExpense.titleValue &&
                    oldExpense.amount.toString() == currentExpense.amountValue &&
                    oldExpense.description == currentExpense.descriptionValue &&
                    oldExpense.category == currentExpense.selectedCategory&&
                    oldExpense.date == currentExpense.selectedLocalDate
                    ){
                    _event.emit(UiEvent.ShowSnackBar("no item changed"))
                }else{
                    _state.update {
                        it.copy(isSaveEnabled = false)
                    }
                    updateExpense(currentEntityExpense)
                    _event.emit(UiEvent.ShowSnackBar("Expense Updated"))

                }

            }

        }else{
            addExpense(currentEntityExpense)
            _state.update {
                it.copy(
                    isSaveEnabled = false
                )
            }
            viewModelScope.launch {
                val randomIndex = (0..snackbarAddMessage.lastIndex).random()
                _event.emit(UiEvent.ShowSnackBar(snackbarAddMessage[randomIndex]))
            }
        }
    }

    fun addExpense(expense: ExpenseUi){
        viewModelScope.launch {
            repository.add(mapper.toDomain(expense))
        }
    }
    fun updateExpense(expense: ExpenseUi){
        viewModelScope.launch {
            repository.update(mapper.toDomain(expense))
        }
    }
}