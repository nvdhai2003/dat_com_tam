package nvdhai2003.fpl.asm_ph57651.ui.screens

import android.util.Log
import android.widget.Toast
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import nvdhai2003.fpl.asm_ph57651.R
import nvdhai2003.fpl.asm_ph57651.model.Food
import nvdhai2003.fpl.asm_ph57651.model.FoodResponse
import nvdhai2003.fpl.asm_ph57651.navigation.Screen
import nvdhai2003.fpl.asm_ph57651.ui.components.AddFoodDialog
import nvdhai2003.fpl.asm_ph57651.ui.components.CustomButton
import nvdhai2003.fpl.asm_ph57651.ui.components.CustomStatusBar
import nvdhai2003.fpl.asm_ph57651.ui.components.FoodDetailDialog
import nvdhai2003.fpl.asm_ph57651.ui.components.UpdateFoodDialog
import nvdhai2003.fpl.asm_ph57651.viewmodel.FoodViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminHomeScreen(navController: NavController, foodViewModel: FoodViewModel) {
    CustomStatusBar()
    val search = remember { mutableStateOf("") }
    val foodState = foodViewModel.foods.observeAsState(initial = emptyList())
    val isLoading = foodViewModel.isLoading.collectAsState()
    var showDialog = remember { mutableStateOf(false) }

    val context = LocalContext.current
    Log.d("zzzzzzzzzzz", "FoodScreen: ${foodState.value}")
    val foods = foodState.value

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xFF252121)),
        ) {
            Spacer(modifier = Modifier.height(60.dp))
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
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.End
                ) {
                    Image(
                        painter = painterResource(R.drawable.addcircle_linear_32px),
                        contentDescription = null,
                        modifier = Modifier.clickable(onClick = { showDialog.value = true })
                    )
                }

            }

            if (showDialog.value) {
                AddFoodDialog(
                    onDismiss = { showDialog.value = false },
                    onSubmit = { name, price, thumbnail, description ->
                        foodViewModel.addFood(
                            name = name,
                            price = price,
                            thumbnail = thumbnail,
                            description = description,
                            onSuccess = {
                                showDialog.value = false
                            },
                            onError = { errorMessage ->
                                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                            }
                        )
                    }
                )
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
                    FoodAdminList(navController, foodViewModel)
                }
            }
        }
    }

}

@Composable
fun FoodAdminList(navController: NavController, foodViewModel: FoodViewModel) {
    val foods = foodViewModel.foods.observeAsState(emptyList())
    val context = LocalContext.current
    var errorMessage = remember { mutableStateOf<String?>(null) }
    LazyColumn(
        state = rememberLazyListState(),
        contentPadding = PaddingValues(horizontal = 19.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(foods.value.size) { index ->
            FoodAdminItem(
                food = foods.value[index],
                onDeleteClick = {
                    foodViewModel.deleteFood(foods.value[index].id, onSuccess = {
                        Toast.makeText(context, "Xóa món ăn thành công", Toast.LENGTH_SHORT).show()
                    }, onError = { error ->
                        errorMessage.value = error
                        Toast.makeText(context, "Xóa thất bại: $error", Toast.LENGTH_SHORT).show()
                    })
                },
                onUpdateClick = { updatedFood ->
                    foodViewModel.updateFood(
                        updatedFood.id,
                        updatedFood.name,
                        updatedFood.price,
                        updatedFood.thumbnail,
                        updatedFood.description,
                        onSuccess = {
                            Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT)
                                .show()
                        },
                        onError = { error ->
                            Toast.makeText(context, "Cập nhật thất bại: $error", Toast.LENGTH_SHORT)
                                .show()
                        })
                }
            )
        }
    }

}


@Composable
fun FoodAdminItem(food: Food, onDeleteClick: (id: Int) -> Unit, onUpdateClick: (Food) -> Unit) {
    val openDialog = remember { mutableStateOf(false) }
    val openDetailDialog = remember { mutableStateOf(false) }
    val openUpdateDialog = remember { mutableStateOf(false) }
    if (openDialog.value) {
        AlertDialog(
            title = {
                Text(text = "Xóa món ăn", color = Color.Black, fontWeight = FontWeight.Bold)
            },
            text = {
                Text(text = "Bạn chắc chắn muốn xóa món ăn ${food.name}?", color = Color.Black)
            },
            onDismissRequest = {
                openDialog.value = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                        onDeleteClick(food.id)
                    }
                ) {
                    Text("Xóa", color = Color(0xFFFE724C))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                    }
                ) {
                    Text("Không", color = Color.Black)
                }
            }
        )
    }

    if (openUpdateDialog.value) {
        UpdateFoodDialog(
            food = food,
            onDismiss = { openUpdateDialog.value = false },
            onUpdate = { name, price, thumbnail, description ->
                val updatedFood = food.copy(
                    name = name,
                    price = price,
                    thumbnail = thumbnail,
                    description = description
                )
                onUpdateClick(updatedFood)
                openUpdateDialog.value = false
            },
        )
    }


    if (openDetailDialog.value) {
        FoodDetailDialog(food = food, onDismiss = { openDetailDialog.value = false })
    }
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2F2D2D)),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .wrapContentHeight()
            .padding(5.dp, 4.dp)
            .clickable { openDetailDialog.value = true }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = food.thumbnail,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(100.dp)
                    .height(80.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = food.name,
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "${food.price}k",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFE724C)
                    ),
                )
            }
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.End
            ) {
                Image(
                    painter = painterResource(R.drawable.edit_linear_32px),
                    contentDescription = null,
                    modifier = Modifier.clickable(onClick = {
                        openUpdateDialog.value = true
                    })
                )
                Spacer(modifier = Modifier.height(8.dp))
                Image(
                    painter = painterResource(R.drawable.trash_linear_32px),
                    contentDescription = null,
                    modifier = Modifier.clickable(onClick = {
                        openDialog.value = true
                    })
                )
            }
        }


    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAdminHome() {
    val foodViewModel = FoodViewModel()
    val navController = rememberNavController()
    AdminHomeScreen(navController, foodViewModel)
}