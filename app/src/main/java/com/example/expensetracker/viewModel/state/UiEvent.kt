package com.example.expensetracker.viewModel.state

sealed class UiEvent {
    data class ShowSnackBar(val message: String): UiEvent()
    data class Navigate(val route: String): UiEvent()
    data class DeleteExpense(val expenseId: Long): UiEvent()
}


val snackbarAddMessage = listOf(
    "Well… money was spent 😂",
    "Added! At least you’re tracking it 😉📊",
    "Money spent wisely… we hope 😜💳",
    "Logged! Every dollar has a story 📖💰",
    "Oops… money gone 😅💸",
    "Spent! But tracked 😉📊",
    "There goes the budget 😆📉",
    "Money spent. No regrets 😎💳",
)