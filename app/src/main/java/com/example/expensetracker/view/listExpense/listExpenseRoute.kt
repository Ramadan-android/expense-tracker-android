package com.example.expensetracker.view.listExpense

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.expensetracker.route.Routes

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.ExpenseListRoute(navController: NavController){
    composable(route = Routes.home){ExpenseListScreen(navController)}
}

fun NavController.ToHome(){
    navigate(Routes.home){
        popUpTo(Routes.onboarding){
            inclusive = true
        }

    }
}