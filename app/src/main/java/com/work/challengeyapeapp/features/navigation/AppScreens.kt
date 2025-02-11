package com.work.challengeyapeapp.features.navigation

sealed class AppScreens (val route: String){
    object SplashScreen : AppScreens("splash_screen")
    object HomeScreen : AppScreens("home_screen")
    object DetailScreen : AppScreens("detail_screen")
    object MapScreen : AppScreens("map_screen")
}
