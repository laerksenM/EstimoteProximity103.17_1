package dtu

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


@Composable
fun NavDemo() {
    val navController = rememberNavController()
    NavDemoHost(navController)

}
@Composable
fun NavDemoHost (navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "Startside",
    ) {
        composable("Startside") {
            StartsideView(navController = navController)
        }
        composable("Agegroup") {
            AgegroupView(navController = navController)
        }
        composable("GameBarn") {
            GameViewBarn(navController = navController)
        }
        composable("GameTeen") {
            GameViewTeen(navController = navController)
        }
    }
}