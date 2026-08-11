package com.example.expensetracker.viewModel.state

data class OnboardingUiState(
    val nameValue: String = "",
    val salaryValue: String = "",
    val enableNextButton: Boolean = false,
)