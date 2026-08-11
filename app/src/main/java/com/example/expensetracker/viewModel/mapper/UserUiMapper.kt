package com.example.expensetracker.viewModel.mapper

import com.example.expensetracker.domain.model.User
import com.example.expensetracker.viewModel.model.UserUi
import javax.inject.Inject

class UserUiMapper @Inject constructor() {

    fun toDomain(ui: UserUi): User =
        User(
            name = ui.name,
            monthlyBudget = ui.monthlyBudget
        )
    fun toUi(domain: User): UserUi =
        UserUi(
            name = domain.name,
            monthlyBudget = domain.monthlyBudget
        )
}