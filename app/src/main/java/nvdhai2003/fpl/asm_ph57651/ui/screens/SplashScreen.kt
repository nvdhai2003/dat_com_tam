package nvdhai2003.fpl.asm_ph57651.ui.screens

import android.widget.Toast
import android.window.SplashScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import nvdhai2003.fpl.asm_ph57651.R
import nvdhai2003.fpl.asm_ph57651.navigation.Screen
import nvdhai2003.fpl.asm_ph57651.ui.components.CustomStatusBar
import nvdhai2003.fpl.asm_ph57651.viewmodel.AuthViewModel

@Composable
fun SplashScreen(navController: NavController?, authViewModel: AuthViewModel = viewModel()) {
    CustomStatusBar()
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        delay(3000L)

        if (navController != null) {
            if (authViewModel.isUserLoggedIn() && !authViewModel.isAdminUser()) {
                navController.navigate(Screen.BottomNavigation.route) {
                    popUpTo(Screen.SplashScreen.route) { inclusive = true }
                }
            } else {
                navController.navigate(Screen.LoginScreen.route) {
                    popUpTo(Screen.SplashScreen.route) { inclusive = true }
                }
            }
        } else {
            Toast.makeText(context, "Lỗi", Toast.LENGTH_LONG).show()
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF282222)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSplash() {
    SplashScreen(navController = NavController(LocalContext.current))
}
