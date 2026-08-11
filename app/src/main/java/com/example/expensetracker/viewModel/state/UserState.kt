package com.example.expensetracker.viewModel.state

data class UserState(
    val userStateLogin: UserStateLogin = UserStateLogin.NoUser,
)

enum class UserStateLogin{
    HasUser,
    NoUser
}