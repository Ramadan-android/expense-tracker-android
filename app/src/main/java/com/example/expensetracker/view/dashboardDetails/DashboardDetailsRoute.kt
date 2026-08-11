package com.example.expensetracker.view.dashboardDetails

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.expensetracker.route.Routes

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.DashboardDetails(){
    composable(Routes.dashboard_Details){
        DashboardDetailsScreen()
    }
}

fun NavController.ToDashboardDetails(){
    navigate(Routes.dashboard_Details)

}