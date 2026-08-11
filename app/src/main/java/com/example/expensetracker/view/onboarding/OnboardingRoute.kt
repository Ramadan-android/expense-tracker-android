package com.example.expensetracker.view.onboarding

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.expensetracker.route.Routes


fun NavGraphBuilder.onboardingRoute(navController: NavController){
    composable(route = Routes.onboarding){OnboardingScreen(navController = navController)}
}
fun NavController.toOnboarding(){
    navigate(Routes.onboarding)
}
