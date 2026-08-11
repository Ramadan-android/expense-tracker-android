package com.example.expensetracker.viewModel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.domain.model.User
import com.example.expensetracker.domain.repository.DataRepository
import com.example.expensetracker.domain.repository.UserRepositry
import com.example.expensetracker.viewModel.mapper.ExpenseUiMapper
import com.example.expensetracker.viewModel.model.ExpenseUi
import com.example.expensetracker.viewModel.state.DashboardUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.WeekFields
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class DashboardViewModel @Inject constructor(
    repository: DataRepository,
    userRepository: UserRepositry,
    mapper: ExpenseUiMapper,
): ViewModel(){
    private val _state = MutableStateFlow(DashboardUiState())
    val state = _state.asStateFlow()

    @RequiresApi(Build.VERSION_CODES.O)
    val sourseExpenseList: StateFlow<List<ExpenseUi>> = mapper.toUiList(repository.get())
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    private val user: StateFlow<User?> = userRepository.getUser()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null

        )

    init {
        viewModelScope.launch {
            combine(
                flow = sourseExpenseList,
                flow2 = user
            ){
                expenseList, user ->
                val today = expenseList.filter {
                        it.date == LocalDate.now()
                    }.sumOf { it.amount  }
                val week = expenseList.filter {
                    val weekFields = WeekFields.of(DayOfWeek.SATURDAY,1)
                    (it.date.year == LocalDate.now().year && it.date.get(weekFields.weekOfWeekBasedYear()) == LocalDate.now().get(weekFields.weekOfWeekBasedYear()))
                }.sumOf { it.amount  }
                val month = expenseList.filter {
                    (it.date.year == LocalDate.now().year && it.date.month == LocalDate.now().month )
                }.sumOf { it.amount  }
                val salery = user?.monthlyBudget?: 0.0
                val balance = salery - month
                val todayDate = LocalDate.now()
                val startWeek = todayDate.minusDays(6)
                val startMonth = todayDate.withDayOfMonth(1)
                val monthLength = todayDate.lengthOfMonth()
                val weekChartData: Map<LocalDate, Double> =
                    (0..6).associate { offset ->
                        val date = startWeek.plusDays(offset.toLong())
                        val totalForDay = expenseList
                            .filter { it.date == date }
                            .sumOf { it.amount }
                        date to totalForDay
                    }

                val monthChartData: Map<LocalDate, Double> =
                    (1..monthLength).associate { offset ->
                        val date = startMonth.plusDays(offset.toLong())
                        val totalForDay = expenseList
                            .filter { it.date == date }
                            .sumOf { it.amount }
                        date to totalForDay
                    }

                DashboardUiState(
                    totalDay = today,
                    totalWeek = week,
                    totalMonth = month,
                    salery = salery,
                    balance = balance,
                    weekChart = weekChartData,
                    monthChart = monthChartData
                    )

            }.collect { dashboardUiState ->
                _state.value = dashboardUiState
            }

        }
    }

}