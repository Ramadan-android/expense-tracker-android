package com.example.expensetracker

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.expensetracker.route.ExpenseNavGraph
import com.example.expensetracker.route.Routes
import com.example.expensetracker.ui.theme.ExpenseTrackerTheme
import com.example.expensetracker.viewModel.UserStateViewModel
import com.example.expensetracker.viewModel.state.UserStateLogin
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExpenseTrackerApp()
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ExpenseTrackerApp(
    userViewModel: UserStateViewModel = hiltViewModel()
) {
    val state by userViewModel.state.collectAsState()
    val startRoute = if(state.userStateLogin == UserStateLogin.NoUser) Routes.onboarding else Routes.home
    val navController = rememberNavController()
    ExpenseTrackerTheme {
        ExpenseNavGraph(navController = navController, route = startRoute)
    }
}
