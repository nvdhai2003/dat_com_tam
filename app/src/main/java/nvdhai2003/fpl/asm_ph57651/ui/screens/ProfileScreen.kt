package nvdhai2003.fpl.asm_ph57651.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import nvdhai2003.fpl.asm_ph57651.navigation.Screen
import nvdhai2003.fpl.asm_ph57651.viewmodel.AuthViewModel
import nvdhai2003.fpl.asm_ph57651.viewmodel.FoodViewModel

@Composable
fun ProfileScreen(navController: NavController, authViewModel: AuthViewModel) {
    var isLoggingOut = remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.fillMaxSize().background(color = Color(0xFF252121)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (isLoggingOut.value) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(48.dp),
                color = Color(0xFFFE724C),
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )
        } else {
            Button(
                onClick = {
                    isLoggingOut.value = true
                    authViewModel.logout(
                        onSuccess = {
                            navController.navigate(Screen.LoginScreen.route) {
                                popUpTo(Screen.LoginScreen.route) { inclusive = true }
                            }
                        },
                        onError = { errorMessage ->
                            isLoggingOut.value = false
                        }
                    )
                }
            ) {
                Text(text = "Đăng xuất")
            }
        }
    }
}