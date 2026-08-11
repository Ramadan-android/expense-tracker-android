package com.example.expensetracker.view.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.expensetracker.view.composable.CoustemButton
import com.example.expensetracker.view.composable.ExpenseTextField
import com.example.expensetracker.view.composable.TextFs26Aline
import com.example.expensetracker.view.composable.VerticalSpacer
import com.example.expensetracker.view.listExpense.ToHome
import com.example.expensetracker.viewModel.OnboardingViewModel
import com.example.expensetracker.viewModel.state.OnboardingUiState
import com.example.expensetracker.viewModel.state.UiEvent

@Composable
fun OnboardingScreen(
    navController: NavController,
    viewModel: OnboardingViewModel = hiltViewModel(),
    ) {
    val state by viewModel.state.collectAsState()
    Scaffold(
        containerColor = Color(0xff020617),
    ) {paddingValues ->
        OnboardingContent(
            modifier = Modifier.padding(paddingValues),
            state = state,
            onChangeName = {viewModel.onChangeName(it)},
            onCahngeSalary = {viewModel.onChangeSalary(it)},
            onClickNext = viewModel::onClickNext,
            )
    }
    LaunchedEffect(viewModel) {
        viewModel.event.collect { event ->
            when(event){
                is UiEvent.DeleteExpense -> TODO()
                is UiEvent.Navigate -> navController.ToHome()
                is UiEvent.ShowSnackBar -> TODO()
            }
        }
    }
}

@Composable
private fun OnboardingContent(
    modifier: Modifier = Modifier,
    state: OnboardingUiState,
    onChangeName: (String) -> Unit,
    onCahngeSalary: (String) -> Unit,
    onClickNext: () -> Unit,

){
    Column (
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextFs26Aline(
            text = "Welcome 👋",
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.headlineMedium,
            color = Color(0xffE5E7EB),
            align = TextAlign.Start

            )
        VerticalSpacer(24.dp)
        ExpenseTextField(
            value = state.nameValue,
            onValueChange = onChangeName,
            hint = "Name",
            singleLine = true,
        )
        VerticalSpacer(16.dp)
        ExpenseTextField(
            value = state.salaryValue,
            onValueChange = onCahngeSalary,
            hint = "Salary",
            singleLine = true,
            keyboardType = KeyboardType.Number
        )
        VerticalSpacer(32.dp)
        CoustemButton(
            onClickSave = onClickNext,
            text = "Next",
            enabled = state.enableNextButton
        )
    }
}

@Preview
@Composable
private fun OnboardingContentPreview() {
    OnboardingContent(
        state = OnboardingUiState(),
        onChangeName = {},
        onCahngeSalary = {},
        onClickNext = {}

    )
}