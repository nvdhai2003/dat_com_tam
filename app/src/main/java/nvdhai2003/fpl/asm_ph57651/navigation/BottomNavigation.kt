package nvdhai2003.fpl.asm_ph57651.navigation

import android.annotation.SuppressLint
import android.view.WindowInsetsController
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Text
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import nvdhai2003.fpl.asm_ph57651.ui.screens.CartScreen
import nvdhai2003.fpl.asm_ph57651.ui.screens.HistoryScreen
import nvdhai2003.fpl.asm_ph57651.ui.screens.HomeScreen
import nvdhai2003.fpl.asm_ph57651.ui.screens.ProfileScreen
import nvdhai2003.fpl.asm_ph57651.R
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import nvdhai2003.fpl.asm_ph57651.ui.components.CustomStatusBar
import nvdhai2003.fpl.asm_ph57651.viewmodel.AuthViewModel
import nvdhai2003.fpl.asm_ph57651.viewmodel.CartViewModel
import nvdhai2003.fpl.asm_ph57651.viewmodel.FoodViewModel

@SuppressLint("NewApi")
@Composable
fun BottomNav() {
    val selectedIndex = remember { mutableStateOf(0) }
    val foodViewModel = FoodViewModel()
    val authViewModel = AuthViewModel(LocalContext.current)
    val navController = rememberNavController()
    val cartViewModel = CartViewModel()


    val currentStatusBarColor = when (selectedIndex.value) {
        0 -> Color(0xFF312F2E)
        1 -> Color(0xFF252121)
        2 -> Color(0xFF252121)
        3 -> Color(0xFF252121)
        else -> Color.Black
    }

    val window = (LocalContext.current as? ComponentActivity)?.window
    window?.statusBarColor = currentStatusBarColor.toArgb()

    window?.insetsController?.setSystemBarsAppearance(
        0,
        WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
    )
    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                selectedIndex = selectedIndex.value,
                onItemSelected = { selectedIndex.value = it }
            )
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            when (selectedIndex.value) {
                0 -> HomeScreen(navController, foodViewModel, cartViewModel)
                1 -> HistoryScreen()
                2 -> CartScreen(cartViewModel)
                3 -> ProfileScreen(navController, authViewModel)
            }
        }
    }
}

@Composable
fun BottomNavigationBar(selectedIndex: Int, onItemSelected: (Int) -> Unit) {
    BottomNavigation(
        backgroundColor = Color(0xFF312C2C),
        contentColor = Color.White,
        elevation = 5.dp,
        modifier = Modifier.height(80.dp)
    ) {
        BottomNavigationItem(
            icon = {
                Image(
                    painter = painterResource(id = R.drawable.home_linear_32px),
                    contentDescription = "Home",
                    modifier = Modifier.size(24.dp),
                    colorFilter = if (selectedIndex == 0) {
                        ColorFilter.tint(Color(0xFFFE724C))
                    } else {
                        ColorFilter.tint(Color.White)
                    }
                )
            },
            label = { Text("Trang chủ") },
            selected = selectedIndex == 0,
            onClick = { onItemSelected(0) },
            selectedContentColor = Color(0xFFFE724C),
            unselectedContentColor = Color.White,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
        BottomNavigationItem(
            icon = {
                Image(
                    painter = painterResource(id = R.drawable.card_linear_32px),
                    contentDescription = "History",
                    modifier = Modifier.size(24.dp),
                    colorFilter = if (selectedIndex == 1) {
                        ColorFilter.tint(Color(0xFFFE724C))
                    } else {
                        ColorFilter.tint(Color.White)
                    }
                )
            },
            label = { Text("Lịch sử") },
            selected = selectedIndex == 1,
            onClick = { onItemSelected(1) },
            selectedContentColor = Color(0xFFFE724C),
            unselectedContentColor = Color.White,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
        BottomNavigationItem(
            icon = {
                Image(
                    painter = painterResource(id = R.drawable.shoppingcart_bold_32px),
                    contentDescription = "Cart",
                    modifier = Modifier.size(24.dp),
                    colorFilter = if (selectedIndex == 2) {
                        ColorFilter.tint(Color(0xFFFE724C))
                    } else {
                        ColorFilter.tint(Color.White)
                    }
                )
            },
            label = { Text("Giỏ hàng") },
            selected = selectedIndex == 2,
            onClick = { onItemSelected(2) },
            selectedContentColor = Color(0xFFFE724C),
            unselectedContentColor = Color.White,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
        BottomNavigationItem(
            icon = {
                Image(
                    painter = painterResource(id = R.drawable.user_linear_32px),
                    contentDescription = "Profile",
                    modifier = Modifier.size(24.dp),
                    colorFilter = if (selectedIndex == 3) {
                        ColorFilter.tint(Color(0xFFFE724C))
                    } else {
                        ColorFilter.tint(Color.White)
                    }
                )
            },
            label = { Text("Hồ sơ") },
            selected = selectedIndex == 3,
            onClick = { onItemSelected(3) },
            selectedContentColor = Color(0xFFFE724C),
            unselectedContentColor = Color.White,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}

