package com.example.expensetracker.view.addEditExpense

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.expensetracker.domain.model.ExpenseCategory
import com.example.expensetracker.view.composable.CoustemButton
import com.example.expensetracker.view.composable.DatePickerExample
import com.example.expensetracker.view.composable.DeleteIcon
import com.example.expensetracker.view.composable.ExpenseTextField
import com.example.expensetracker.view.composable.SingleChoiceWrapFilter
import com.example.expensetracker.view.composable.VerticalSpacer
import com.example.expensetracker.viewModel.AddEditViewModel
import com.example.expensetracker.viewModel.state.AddEditExpenseUiState
import com.example.expensetracker.viewModel.state.ScreenMode
import com.example.expensetracker.viewModel.state.UiEvent
import com.example.notaya.view.composable.dialog.DeleteAlertDialog

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddEditExpenseScreen(
    navController: NavController,
    viewModel: AddEditViewModel = hiltViewModel()
    ){
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState,
            modifier = Modifier.clip(shape = RoundedCornerShape(16.dp))
                .background(
                    brush = Brush.linearGradient(
                        listOf(
                            Color(0xFFA9D0FF),
                            Color(0xFF4D81AE),
                            Color(0xFF517FFF)
                        ),
                        start = Offset.Zero,
                        end = Offset.Infinite

                    )
                ),

            ) },
        topBar = {
            TopAppBar(
                title = { Text("Expense Tracker") },
                actions = {
                    if (state.mode is ScreenMode.EDIT){
                        DeleteIcon(onClickIcon = viewModel::onClickDeleteIcon )
                    }
                },
                colors = TopAppBarColors(
                    containerColor = Color(0xff0F172A),
                    titleContentColor = Color(0xffE5E7EB),
                    navigationIconContentColor = Color(0xff0F172A),
                    actionIconContentColor = Color(0xFFF44336),
                    scrolledContainerColor = Color(0xff0F172A)



                )

            )
        },
        containerColor = Color(0xff020617)

    ) {
        AddEditExpenseContent(
            state = state,
            onTitleChange = viewModel::onTitleChange,
            onAmountChange = viewModel::onAmountChange,
            onDescriptionChange = viewModel::onDescriptionChange,
            onCategorySelected = viewModel::onChangeCategory,
            onClickDateDialogButton = viewModel::onClickDateDialogButton,
            onClickSave = viewModel::onClickSave,

            )
        if (state.showDateDialog) {
            val datePickerState = rememberDatePickerState(
                initialSelectedDateMillis = System.currentTimeMillis(),
                selectableDates = object : SelectableDates {
                    @RequiresApi(Build.VERSION_CODES.O)
                    override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                        return utcTimeMillis <= System.currentTimeMillis()
                    }
                }
            )
            DatePickerExample(
                state = datePickerState,
                onClickSet = { viewModel.onClickSetDate(datePickerState) },
                onClickDismiss = viewModel::onDismissDateDialog,
            )
        }
        if (state.showDeleteDialog){
            DeleteAlertDialog(
                onConfirm = viewModel::onConfirmDelete,
                onDismiss = viewModel::onDissmissDelete,
                expenseTitle = state.expenseToDelete?.title?:""
            )
        }
        LaunchedEffect(viewModel) {
            viewModel.event.collect { event ->
                val eventResult =when(event){
                    is UiEvent.Navigate -> TODO()
                    is UiEvent.ShowSnackBar -> {
                         when(event.message){
                            "no item changed" -> snackbarHostState.showSnackbar(
                                message = event.message,
                                duration = SnackbarDuration.Short
                            )
                            else -> {
                                navController.previousBackStackEntry
                                    ?.savedStateHandle?.set("event",event.message)
                                navController.popBackStack()
                            }

                        }


                    }

                    is UiEvent.DeleteExpense -> {
                        navController.previousBackStackEntry
                            ?.savedStateHandle
                            ?.set("deleteExpenseId",event.expenseId)
                        navController.popBackStack()
                    }
                }
//                if (eventResult.toString() == "ActionPerformed"){
//                    if ((eventResult as SnackbarResult).ordinal == 1){
//                        viewModel.onUndoDeleteExpense()
//                    }
//                }
            }
        }

    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddEditExpenseContent(
    state: AddEditExpenseUiState,
    onTitleChange: (String)-> Unit,
    onAmountChange: (String)-> Unit,
    onDescriptionChange: (String)-> Unit,
    onClickSave: ()-> Unit,
    onCategorySelected:(ExpenseCategory)-> Unit,
    onClickDateDialogButton: () -> Unit,
    modifier: Modifier = Modifier
){

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(40.dp),
        modifier = modifier
            .fillMaxSize()

    ) {
        VerticalSpacer(120.dp)
        ExpenseTextField(
            value = state.titleValue,
            onValueChange = onTitleChange,
            hint = "Title",
            singleLine = true
        )
        ExpenseTextField(
            value = state.amountValue,
            onValueChange = onAmountChange,
            keyboardType = KeyboardType.Number,
            hint = "Amount",
            singleLine = true
        )
        ExpenseTextField(
            value = state.descriptionValue,
            onValueChange = onDescriptionChange,
            hint = "Description",
            maxLines = 5

        )
//        CoustemMultiChoiceSegmentedButtonRow(
//            selectedCategory = state.selectedCategory,
//            onCategorySelected = onCategorySelected,
//            categoryList = ExpenseCategory.entries.filterNot { it.name == "ALL" }
//        )
        SingleChoiceWrapFilter(
            selectedCategory = state.selectedCategory,
            onCategorySelected = onCategorySelected,
            categoryList = ExpenseCategory.entries.filterNot { it.name == "ALL" }

        )
        Button(
            onClick =  onClickDateDialogButton,
            colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xff38BDF8)
        )  ) {
            Text("select date")
        }
        Text(text = "Selected Date: ${state.selectedDate}", color = Color(0xffE5E7EB))

        CoustemButton(
            onClickSave = onClickSave,
            text = "Save",
            enabled = state.isSaveEnabled
        )
    }
}