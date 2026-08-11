package com.example.expensetracker.viewModel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.domain.model.ExpenseCategory
import com.example.expensetracker.domain.repository.DataRepository
import com.example.expensetracker.route.Routes
import com.example.expensetracker.viewModel.mapper.ExpenseUiMapper
import com.example.expensetracker.viewModel.model.ExpenseUi
import com.example.expensetracker.viewModel.state.ExpenseListUiState
import com.example.expensetracker.viewModel.state.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListExpenseViewModel @Inject constructor(
    private val repository: DataRepository,
    private val mapper: ExpenseUiMapper,
//    private val savedStateHandle: SavedStateHandle,

): ViewModel(){
    private val _state = MutableStateFlow(ExpenseListUiState())
    val state = _state.asStateFlow()

    private val _event = MutableSharedFlow<UiEvent>()
    val event = _event.asSharedFlow()
    private var recentlyDeletedExpense: ExpenseUi? = null
    @RequiresApi(Build.VERSION_CODES.O)
    val sourseExpenseList: StateFlow<List<ExpenseUi>> = mapper
        .toUiList(repository.get())
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    @RequiresApi(Build.VERSION_CODES.O)
    val expenseList: StateFlow<List<ExpenseUi>> = combine(
        flow = sourseExpenseList,
        flow2 = _state.map { it.searchQuery }.distinctUntilChanged(),
        flow3 = _state.map { it.selectedCategory },
        flow4 = _state.map { it.filterBy }
    ){ expenses, query, selsectedCategory, filterBy ->
        var expenseResult = expenses
        if (query.isNotBlank()){
            expenseResult = expenses.filter { it.title.contains(query,ignoreCase = true) }
        }
        if (filterBy.isNotEmpty()){
            expenseResult = when(filterBy){
                "title" -> expenseResult.sortedBy { it.title }
                "amount" -> expenseResult.sortedBy { it.amount }
                "date" -> expenseResult.sortedBy { it.date }
                else -> expenseResult
            }
        }
        if (selsectedCategory != ExpenseCategory.ALL){
            expenseResult = expenseResult.filter { it.category == selsectedCategory }
        }
        expenseResult
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun onSearchChange(query: String){
        _state.update {
            it.copy(
                searchQuery = query
            )
        }
    }
    fun onCategoryChange(category: ExpenseCategory){
        _state.update {
            it.copy(
                selectedCategory =  category
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onClickFilterDiloge(){
        _state.update {
            it.copy(
                showFilterDialog = true
            )

        }

    }
    fun onConfirmFilterDiloge(filterBy: String){
        _state.update {
            it.copy(
                filterBy = filterBy,
                showFilterDialog = false

            )
        }
    }
    fun onDismissFilterDialoge(){
        _state.update {
            it.copy(
                showFilterDialog = false
            )
        }
    }
    fun navigateToAddScreen(){
        viewModelScope.launch {
            _event.emit(UiEvent.Navigate(Routes.add))
        }
    }
    fun navigateToEditScreen(expense: ExpenseUi){
        viewModelScope.launch {
            _event.emit(UiEvent.Navigate(expense.id.toString()))
        }
    }
    fun navigateToDashboardDetails() {
        viewModelScope.launch {
            _event.emit(UiEvent.Navigate(Routes.dashboard_Details))

        }
    }

    private fun loadExpenseData(expenseId: Long) {
        viewModelScope.launch {
//            recentlyDeletedExpense = repository.getExpenseById(expenseId).firstOrNull()
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onConfirmDelete(expenseId: Long) {
        viewModelScope.launch {
            val deleteExpense = repository.getExpenseById(expenseId).firstOrNull()
            deleteExpense?:return@launch
            val deleteExpenseUi = mapper.toUi(deleteExpense)
            recentlyDeletedExpense = deleteExpenseUi
            deleteExpense(deleteExpenseUi)
            _event.emit(UiEvent.ShowSnackBar("Expense Deleted"))

        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun onUndoDeleteExpense(){
        recentlyDeletedExpense?:return
        addExpense(recentlyDeletedExpense!!)
    }




    @RequiresApi(Build.VERSION_CODES.O)
    fun addExpense(expense: ExpenseUi){
        viewModelScope.launch {
            repository.add(mapper.toDomain(expense))
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun deleteExpense(expense: ExpenseUi){
        viewModelScope.launch {
            repository.delete(mapper.toDomain(expense))
        }
    }


}