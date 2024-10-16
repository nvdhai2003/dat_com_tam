package nvdhai2003.fpl.asm_ph57651.ui.screens

import android.R.attr.onClick
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import nvdhai2003.fpl.asm_ph57651.ui.components.CustomStatusBar
import nvdhai2003.fpl.asm_ph57651.R
import nvdhai2003.fpl.asm_ph57651.model.Food
import nvdhai2003.fpl.asm_ph57651.viewmodel.FoodViewModel
import coil.compose.rememberImagePainter
import com.google.gson.Gson
import nvdhai2003.fpl.asm_ph57651.navigation.Screen
import nvdhai2003.fpl.asm_ph57651.viewmodel.CartViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, foodViewModel: FoodViewModel, cartViewModel: CartViewModel) {
    val search = remember { mutableStateOf("") }
    val foodState = foodViewModel.foods.observeAsState(initial = emptyList())
    val isLoading = foodViewModel.isLoading.collectAsState()
    Log.d("zzzzzzzzzzz", "FoodScreen: ${foodState.value}")
    val foods = foodState.value
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF252121)),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color(0xFF312F2E)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp, 0.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .width(57.dp)
                        .height(48.dp)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = "Cum tứm đim",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp, 0.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(R.drawable.banner),
                    contentDescription = "Banner",
                    modifier = Modifier
                        .width(379.dp)
                        .height(205.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
        TextField(
            value = search.value,
            onValueChange = {
                search.value = it
                foodViewModel.searchFood(it)
            },
            placeholder = { Text("Tìm kiếm", color = Color.Gray) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp, 20.dp)
                .clip(RoundedCornerShape(12.dp)),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color(0xFF2F2D2D),
                focusedTextColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
            leadingIcon = {
                Image(
                    painter = painterResource(R.drawable.searchnormal1_linear_32px),
                    contentDescription = "Search Icon",
                    modifier = Modifier.padding(start = 16.dp, end = 8.dp)
                )
            }
        )

        if (isLoading.value) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(48.dp),
                    color = Color(0xFFFE724C),
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                )
            }
        } else {
            if (foods.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Không tìm thấy món ăn",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            } else {
                FoodList(navController, foodViewModel, cartViewModel)
            }
        }
    }

}


@Preview(showBackground = true)
@Composable
fun PreviewHome() {
    val foodViewModel = FoodViewModel()
    val navController = rememberNavController()
    val cartViewModel = CartViewModel()
    HomeScreen(navController, foodViewModel, cartViewModel)
}