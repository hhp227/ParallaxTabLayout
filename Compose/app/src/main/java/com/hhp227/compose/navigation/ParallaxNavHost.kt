package com.hhp227.compose.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.hhp227.compose.screen.DetailScreen
import com.hhp227.compose.screen.FirstScreen
import com.hhp227.compose.screen.ParallaxTabScreen
import com.hhp227.compose.screen.SecondScreen

object Routes {
    const val First = "first"
    const val Second = "second"
    const val Detail = "detail"
    const val ParallaxTab = "parallaxTab/{group}"

    fun parallaxTab(group: String): String = "parallaxTab/${Uri.encode(group)}"
}

@Composable
fun ParallaxNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Routes.First
    ) {
        composable(Routes.First) {
            FirstScreen(
                selectedRoute = Routes.First,
                onNavigateDrawer = { route -> navController.navigateSingleTop(route) },
                onOpenDetail = { navController.navigate(Routes.Detail) }
            )
        }
        composable(Routes.Second) {
            SecondScreen(
                selectedRoute = Routes.Second,
                onNavigateDrawer = { route -> navController.navigateSingleTop(route) },
                onOpenParallaxTab = { group -> navController.navigate(Routes.parallaxTab(group)) }
            )
        }
        composable(Routes.Detail) {
            DetailScreen(onBack = { navController.popBackStack() })
        }
        composable(
            route = Routes.ParallaxTab,
            arguments = listOf(navArgument("group") { type = NavType.StringType })
        ) { backStackEntry ->
            ParallaxTabScreen(
                title = backStackEntry.arguments?.getString("group").orEmpty().ifBlank { "ParallaxTabFragment" },
                onBack = { navController.popBackStack() }
            )
        }
    }
}

private fun NavHostController.navigateSingleTop(route: String) {
    navigate(route) {
        launchSingleTop = true
        restoreState = true
        popUpTo(graph.startDestinationId) {
            saveState = true
        }
    }
}
