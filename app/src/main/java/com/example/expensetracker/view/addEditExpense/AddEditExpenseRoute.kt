package com.example.expensetracker.view.addEditExpense

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.expensetracker.route.Routes

private const val expenseIdArg = Routes.expenseIdArg
@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.AddEditRoute(navController: NavController){
    composable(
        route = "${Routes.add_Edit}?$expenseIdArg={$expenseIdArg}",
        arguments =listOf(
            navArgument(name = expenseIdArg){
                type = NavType.LongType
                defaultValue = -1L
            }
        )
    ){AddEditExpenseScreen(navController = navController)}
}

fun NavController.toAddExpenseScreen(){
    navigate(Routes.add_Edit)
}
fun NavController.toEditExpenseScreen(expenseId: Long){
    navigate("${Routes.add_Edit}?$expenseIdArg=$expenseId")
}