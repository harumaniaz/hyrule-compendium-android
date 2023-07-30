package com.example.hyrulecompendium.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.hyrulecompendium.ui.screen.detail.DetailScreen
import com.example.hyrulecompendium.ui.screen.home.HomeScreen

object ScreenRoute {
    fun home() = "home"
    fun detail(id: String) = "detail/$id"
}

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    startDestination: String
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(ScreenRoute.home()) {
            HomeScreen(navController = navController)
        }

        composable(
            route = ScreenRoute.detail("{id}"),
            arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                }
            )
        ) {
            val id = it.arguments?.getInt("id") ?: 0
            DetailScreen(
                navController = navController,
                id = id
            )
        }
    }
}