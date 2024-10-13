package nvdhai2003.fpl.asm_ph57651.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import nvdhai2003.fpl.asm_ph57651.model.Food
import nvdhai2003.fpl.asm_ph57651.ui.screens.AdminHomeScreen

import nvdhai2003.fpl.asm_ph57651.ui.screens.FoodDetailScreen
import nvdhai2003.fpl.asm_ph57651.ui.screens.FoodList
import nvdhai2003.fpl.asm_ph57651.ui.screens.LoginScreen
import nvdhai2003.fpl.asm_ph57651.ui.screens.ProfileScreen
import nvdhai2003.fpl.asm_ph57651.ui.screens.SignUpScreen
import nvdhai2003.fpl.asm_ph57651.ui.screens.SplashScreen
import nvdhai2003.fpl.asm_ph57651.viewmodel.AuthViewModel
import nvdhai2003.fpl.asm_ph57651.viewmodel.CartViewModel
import nvdhai2003.fpl.asm_ph57651.viewmodel.FoodViewModel
import kotlin.reflect.typeOf

@Composable
fun ScreenNavigation() {
    val context = LocalContext.current
    val navController = rememberNavController()
    val authViewModel = remember { AuthViewModel(context) }
    val foodViewModel = FoodViewModel()
    val cartViewModel = CartViewModel()
    NavHost(navController, startDestination = Screen.SplashScreen.route) {
        composable(Screen.SplashScreen.route) {
            SplashScreen(
                navController = navController,
                authViewModel = authViewModel
            )
        }
        composable(Screen.LoginScreen.route) {
            LoginScreen(
                navController = navController,
                authViewModel = authViewModel
            )
        }
        composable(Screen.SignupScreen.route) {
            SignUpScreen(
                navController = navController,
                authViewModel = authViewModel
            )
        }
        composable(Screen.BottomNavigation.route) { BottomNav() }
        composable(Screen.FoodList.route) { FoodList(navController, foodViewModel = foodViewModel, cartViewModel) }

        composable(
            route = Screen.FoodDetailScreen.route + "/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val foodId = backStackEntry.arguments?.getInt("id")
            FoodDetailScreen(foodViewModel, foodId)
        }

        composable(Screen.BottomAdminNavigation.route) { BottomAdminNav() }
        composable(Screen.AdminHomeScreen.route) { AdminHomeScreen(navController, foodViewModel = foodViewModel) }
        composable(Screen.ProfileScreen.route) { ProfileScreen(navController, authViewModel) }
    }
}



