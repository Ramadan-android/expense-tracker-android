package com.example.expensetracker.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.domain.model.User
import com.example.expensetracker.domain.repository.UserRepositry
import com.example.expensetracker.viewModel.model.UserUi
import com.example.expensetracker.viewModel.state.UserState
import com.example.expensetracker.viewModel.state.UserStateLogin
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserStateViewModel @Inject constructor(
    private val repository: UserRepositry,

): ViewModel(){
    private val _state = MutableStateFlow(UserState())
    val state = _state.asStateFlow()

    init {
        observeUser()
    }
    private fun observeUser() {
        viewModelScope.launch {
            repository.getUser().collect { user ->
                _state.update {
                    it.copy(
                        userStateLogin = if (user != null)
                            UserStateLogin.HasUser
                        else
                            UserStateLogin.NoUser
                    )
                }
            }
        }
    }
    val getUser = repository.getUser()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )
    fun userState(){
        if (getUser.value != null){
            _state.update {
                it.copy(
                    userStateLogin = UserStateLogin.HasUser
                )
            }
        }
    }


}