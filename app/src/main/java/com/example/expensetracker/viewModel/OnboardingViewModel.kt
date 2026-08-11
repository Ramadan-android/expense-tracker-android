package com.example.expensetracker.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.domain.repository.UserRepositry
import com.example.expensetracker.route.Routes
import com.example.expensetracker.viewModel.mapper.UserUiMapper
import com.example.expensetracker.viewModel.model.UserUi
import com.example.expensetracker.viewModel.state.OnboardingUiState
import com.example.expensetracker.viewModel.state.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val repository: UserRepositry,
    private val mapper: UserUiMapper
): ViewModel(){
    private val _state = MutableStateFlow(OnboardingUiState())
    val state = _state.asStateFlow()
    private val _event = MutableSharedFlow<UiEvent>()
    val event = _event.asSharedFlow()

    fun onChangeName(name: String){
        _state.update {
            it.copy(
                nameValue = name,
                enableNextButton = (name.isNotBlank() && it.salaryValue.isNotBlank())
            )
        }
    }
    fun onChangeSalary(salary: String){
        _state.update {
            it.copy(
                salaryValue = salary,
                enableNextButton = (salary.isNotBlank() && it.nameValue.isNotBlank())
            )
        }
    }

    fun onClickNext(){
        viewModelScope.launch {
            addUser(UserUi(
                name = _state.value.nameValue,
                monthlyBudget = _state.value.salaryValue.toDouble()
            ))
            _event.emit(UiEvent.Navigate(Routes.home))
        }
    }

    private suspend fun addUser(user: UserUi){
        repository.addUser(mapper.toDomain(user))

    }

}