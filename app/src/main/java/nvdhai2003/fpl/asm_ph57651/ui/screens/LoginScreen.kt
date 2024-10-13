package nvdhai2003.fpl.asm_ph57651.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import nvdhai2003.fpl.asm_ph57651.R
import nvdhai2003.fpl.asm_ph57651.navigation.Screen
import nvdhai2003.fpl.asm_ph57651.ui.components.CustomButton
import nvdhai2003.fpl.asm_ph57651.ui.components.CustomStatusBar
import nvdhai2003.fpl.asm_ph57651.viewmodel.AuthViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController, authViewModel: AuthViewModel = viewModel()) {
    CustomStatusBar()
    val username = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    var errorMessage = remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val isLoading = authViewModel.isLoading.collectAsState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                focusManager.clearFocus()
            }
            .background(color = Color(0xFF373232)),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xFF373232)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(0.dp, 0.dp, 20.dp, 20.dp))
                    .background(color = Color(0xFF252121)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.height(60.dp))
                Text(
                    text = "Đăng nhập",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                )
                Spacer(modifier = Modifier.height(20.dp))
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .width(305.dp)
                        .height(294.dp)
                )
                Spacer(modifier = Modifier.height(20.dp))
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color(0xFF373232))
            ) {

                Spacer(modifier = Modifier.height(30.dp))
                TextField(
                    value = username.value,
                    onValueChange = { username.value = it },
                    placeholder = { Text("Tên đăng nhập", color = Color.Gray) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp, 20.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.White,
                        disabledIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent
                    )
                )
                TextField(
                    value = password.value,
                    onValueChange = { password.value = it },
                    placeholder = { Text("Mật khẩu", color = Color.Gray) },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp, 0.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.White,
                        disabledIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent
                    )
                )
                Spacer(modifier = Modifier.height(40.dp))
                if (isLoading.value) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(48.dp)
                            .align(Alignment.CenterHorizontally),
                        color = Color(0xFFFE724C),
                        trackColor = MaterialTheme.colorScheme.surfaceVariant,
                    )
                } else {
                    CustomButton(onClick = {
                        if (username.value.isNotBlank() && password.value.isNotBlank()) {
                            authViewModel.login(
                                username = username.value,
                                password = password.value,
                                onSuccess = {
                                    if (username.value == "admin" && password.value == "123abc") {
                                        Toast.makeText(
                                            context,
                                            "Đăng nhập thành công",
                                            Toast.LENGTH_LONG
                                        )
                                            .show()
                                        navController.navigate(Screen.AdminHomeScreen.route) {
                                            popUpTo(Screen.LoginScreen.route) { inclusive = true }
                                        }
                                    } else {
                                        Toast.makeText(
                                            context,
                                            "Đăng nhập thành công",
                                            Toast.LENGTH_LONG
                                        )
                                            .show()
                                        // Điều hướng tới màn hình chính sau khi đăng nhập thành công
                                        navController.navigate(Screen.BottomNavigation.route)
                                    }

                                },
                                onError = { error ->
                                    errorMessage.value = error
                                    Toast.makeText(context, "Lỗi: $error", Toast.LENGTH_LONG).show()
                                }
                            )
                        } else {
                            Toast.makeText(
                                context,
                                "Vui lòng điền đầy đủ thông tin",
                                Toast.LENGTH_LONG
                            )
                                .show()
                        }
                    }, text = "Đăng Nhập")
                }

                Spacer(modifier = Modifier.height(40.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Bạn chưa có tài khoản?",
                        color = Color.White,
                        fontSize = 16.sp,
                        style = MaterialTheme.typography.bodyMedium.copy(textDecoration = TextDecoration.None)
                    )
                    ClickableText(
                        text = AnnotatedString(
                            " Đăng ký",
                            spanStyle = SpanStyle(
                                color = Color(0xFFFE724C),
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp
                            )
                        ),
                        style = MaterialTheme.typography.bodyMedium.copy(textDecoration = TextDecoration.None),
                        onClick = {
                            navController.navigate(Screen.SignupScreen.route)
                        }
                    )
                }
            }
        }
    }

}


@Preview(showBackground = true)
@Composable
fun PreviewLogin() {
    LoginScreen(navController = NavController(LocalContext.current))
}