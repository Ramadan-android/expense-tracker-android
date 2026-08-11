package com.example.expensetracker.view.listExpense

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.expensetracker.domain.model.ExpenseCategory
import com.example.expensetracker.route.Routes
import com.example.expensetracker.view.addEditExpense.toAddExpenseScreen
import com.example.expensetracker.view.addEditExpense.toEditExpenseScreen
import com.example.expensetracker.view.composable.DashboardTotals
import com.example.expensetracker.view.composable.ExpenseCard
import com.example.expensetracker.view.composable.ExpenseTextField
import com.example.expensetracker.view.composable.SingleChoiceWrapFilter
import com.example.expensetracker.view.composable.TextAlignFsDf16
import com.example.expensetracker.view.composable.TextFs26
import com.example.expensetracker.view.composable.VerticalSpacer
import com.example.expensetracker.view.dashboardDetails.ToDashboardDetails
import com.example.expensetracker.viewModel.DashboardViewModel
import com.example.expensetracker.viewModel.ListExpenseViewModel
import com.example.expensetracker.viewModel.model.ExpenseUi
import com.example.expensetracker.viewModel.state.DashboardUiState
import com.example.expensetracker.viewModel.state.ExpenseListUiState
import com.example.expensetracker.viewModel.state.UiEvent
import com.example.trainingapp.composable.FilterDropdown
import com.example.trainingapp.composable.FilterIcon
import kotlin.time.ExperimentalTime


@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ExpenseListScreen(
    navController: NavController,
    viewModel: ListExpenseViewModel = hiltViewModel(),
    dashboardViewModel: DashboardViewModel = hiltViewModel()
){
    val state by viewModel.state.collectAsState()
    val dashboardState by dashboardViewModel.state.collectAsState()
    val sourseExpenseList by viewModel.sourseExpenseList.collectAsState()
    val expenseList by viewModel.expenseList.collectAsState()
    val snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
    val savedStateHandle = navController.currentBackStackEntry?.savedStateHandle
    val handleEvent = savedStateHandle?.getStateFlow<String?>("event",null)
    val handleDeleteExpenseId = savedStateHandle?.getStateFlow<Long?>("deleteExpenseId",null)



    Scaffold(
        topBar = { TopAppBar(
            title = { TextAlignFsDf16(
                text = "your Expense 🙌      😎",
                align = TextAlign.Center,
                fontSize = 20.sp
                ) },
            colors = TopAppBarColors(
                containerColor = Color(0xff0F172A),
                titleContentColor = Color(0xffE5E7EB),
                navigationIconContentColor = Color(0xff0F172A),
                actionIconContentColor = Color(0xff0F172A),
                scrolledContainerColor = Color(0xff0F172A)



            )
        ) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = viewModel::navigateToAddScreen,
                containerColor = Color(0xff38BDF8)
            ) {
                TextFs26(text = "+")
            }
       },
        snackbarHost = { SnackbarHost(snackbarHostState,
            modifier = Modifier
                .clip(shape = RoundedCornerShape(16.dp))
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
        containerColor = Color(0xff020617)
    ) {

        innerPadding ->
        if (sourseExpenseList.isEmpty()){
              Column(
                  modifier = Modifier.fillMaxSize()
                      .padding(innerPadding),
              ) {
                  VerticalSpacer(50.dp)
                  DashboardTotals(dashboardState)
                  VerticalSpacer(200.dp)
                  EmptyExpense(
                      text = "Add Expense to view here",
                      onClickAddText = viewModel::navigateToAddScreen
                  )
              }


        }else {
            ExpenseListContent(
                state = state,
                expenseList = expenseList,
                dashboardState = dashboardState,
                onSearchChange = viewModel::onSearchChange,
                onClickFilter = viewModel::onClickFilterDiloge,
                innerPadding = innerPadding,
                onCategorySelected = viewModel::onCategoryChange,
                onClickCard = {
                    viewModel.navigateToEditScreen(it)
                },
                onClickDetails = viewModel::navigateToDashboardDetails
            )
            LaunchedEffect(Unit){
                handleEvent?.collect { event ->
                    event?.let {
                            snackbarHostState.showSnackbar(
                                message = event,
                                duration = SnackbarDuration.Short
                            )
                    }
                    savedStateHandle.remove<String>("event")
                }
            }

        }
        LaunchedEffect(Unit) {
            handleDeleteExpenseId?.collect {expenseId ->
                expenseId?.let {
                    viewModel.onConfirmDelete(expenseId)
                }
                savedStateHandle.remove<String>("deleteExpenseId")
            }        }
        LaunchedEffect(viewModel) {
            viewModel.event.collect { event ->
                val eventResult = when(event){
                    is UiEvent.Navigate ->{
                        when(event.route){
                            Routes.add -> navController.toAddExpenseScreen()
                            Routes.dashboard_Details -> navController.ToDashboardDetails()
                            else -> navController.toEditExpenseScreen(event.route.toLong())
                        }
                    }
                    is UiEvent.ShowSnackBar ->{
                        snackbarHostState.showSnackbar(
                            message = event.message,
                            actionLabel = "Undo.?",
                            duration = SnackbarDuration.Long
                        )
                    }
                    is UiEvent.DeleteExpense -> {}
                }
                if (eventResult.toString() == "ActionPerformed"){
                    if ((eventResult as SnackbarResult).ordinal == 1){
                        viewModel.onUndoDeleteExpense()
                    }
                }

            }
        }

        if (state.showFilterDialog) FilterDropdown(
            onClickConfFilter = viewModel::onConfirmFilterDiloge,
            onClickDismissFilter = viewModel::onDismissFilterDialoge
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalTime::class)
@Composable
fun ExpenseListContent(
    state: ExpenseListUiState,
    dashboardState: DashboardUiState,
    expenseList: List<ExpenseUi>,
    onSearchChange: (String) -> Unit,
    onClickFilter: () -> Unit,
    onClickDetails: () -> Unit,
    innerPadding: PaddingValues,
    onCategorySelected: (ExpenseCategory) -> Unit,

    onClickCard: (ExpenseUi) -> Unit,
){
    LazyColumn(
        modifier = Modifier.padding(innerPadding)
    ) {
        stickyHeader {

            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color(0xff020617))
            ) {
                ExpenseTextField(
                    value = state.searchQuery,
                    onValueChange = onSearchChange,
                    hint = "Search",
                    showSearchIcon = true,
                    singleLine = true,
                    horizontalPadding = 0.dp
                )
                FilterIcon(onClickFilterIcon = onClickFilter)
            }
        }
        item {
            SingleChoiceWrapFilter(
                selectedCategory = state.selectedCategory,
                onCategorySelected = onCategorySelected,
            )
        }
        item {
            DashboardTotals(
                dashboardState = dashboardState,
                onClickDetails = onClickDetails
                )
        }
        if (expenseList.isEmpty()){

            item {
                VerticalSpacer(50.dp)
                EmptyExpense(text = "No Result Found")
            }
        }
        items(items = expenseList, key = {it.id}){
            ExpenseCard(
                expense = it,
                onClickCard = onClickCard,
                modifier = Modifier.animateItem()
            )

        }
    }
}



@Composable
private fun EmptyExpense(
    modifier: Modifier = Modifier,
    text: String,
    onClickAddText: () -> Unit = {},
) {
        Text(
            text = text,
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClickAddText),
            textAlign = TextAlign.Center,
            fontSize = 28.sp,
            color = Color.Gray,
        )
}