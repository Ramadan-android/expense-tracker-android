package com.example.expensetracker.route

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.expensetracker.view.addEditExpense.AddEditRoute
import com.example.expensetracker.view.dashboardDetails.DashboardDetails
import com.example.expensetracker.view.listExpense.ExpenseListRoute
import com.example.expensetracker.view.onboarding.onboardingRoute

@RequiresApi(Build.VERSION_CODES.O)
@Composable

fun ExpenseNavGraph(navController: NavHostController,route: String) {
    NavHost(
        navController = navController,
        startDestination = route
    ) {
        onboardingRoute(navController = navController)
        AddEditRoute(navController = navController)
        ExpenseListRoute(navController = navController)
        DashboardDetails()


    }
}