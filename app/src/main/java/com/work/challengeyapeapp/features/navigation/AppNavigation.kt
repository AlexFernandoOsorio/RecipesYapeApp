package com.work.challengeyapeapp.features.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.work.challengeyapeapp.features.detailScreen.DetailScreen
import com.work.challengeyapeapp.features.detailScreen.DetailScreenViewModel
import com.work.challengeyapeapp.features.homeScreen.HomeScreen
import com.work.challengeyapeapp.features.homeScreen.HomeScreenViewModel
import com.work.challengeyapeapp.features.homeScreen.SplashScreen
import com.work.challengeyapeapp.features.mapScreen.MapScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreens.SplashScreen.route) {
        composable(route= AppScreens.SplashScreen.route){
            SplashScreen(navController = navController)
        }
        composable(route = AppScreens.HomeScreen.route) {
            val viewmodel = hiltViewModel<HomeScreenViewModel>()
            HomeScreen(viewModel = viewmodel,navController)
        }
        composable(route = AppScreens.DetailScreen.route + "/{id}") {
            val id = it.arguments?.getString("id")
            val viewModel = hiltViewModel<DetailScreenViewModel>()
            DetailScreen(idRecipe = id.toString(), viewModel = viewModel, navController)
        }
        composable(route = AppScreens.MapScreen.route + "/{marker}") {
            val marker = it.arguments?.getString("marker")
            MapScreen(mark = marker.toString(), navController)
        }
    }
}
