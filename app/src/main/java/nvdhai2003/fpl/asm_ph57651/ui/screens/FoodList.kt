package nvdhai2003.fpl.asm_ph57651.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nvdhai2003.fpl.asm_ph57651.R
import androidx.navigation.NavController
import coil.compose.AsyncImage
import nvdhai2003.fpl.asm_ph57651.model.Food
import nvdhai2003.fpl.asm_ph57651.navigation.Screen
import nvdhai2003.fpl.asm_ph57651.ui.components.FoodDetailDialog
import nvdhai2003.fpl.asm_ph57651.viewmodel.CartViewModel
import nvdhai2003.fpl.asm_ph57651.viewmodel.FoodViewModel

@Composable
fun FoodList(
    navController: NavController,
    foodViewModel: FoodViewModel,
    cartViewModel: CartViewModel
) {
    val foods = foodViewModel.foods.observeAsState(emptyList())
    val context = LocalContext.current

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 19.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(foods.value.size) { index ->
            FoodItem(
                food = foods.value[index],
                onAddToCart = {
                    Toast.makeText(context, "Added ${foods.value[index].name} to cart", Toast.LENGTH_SHORT).show()
                    cartViewModel.addToCart(foods.value[index])
                }
            )
        }
    }
}


@Composable
fun FoodItem(food: Food, onAddToCart: () -> Unit) {
    val openDetailDialog = remember { mutableStateOf(false) }
    val context = LocalContext.current
    if (openDetailDialog.value) {
        FoodDetailDialog(food = food, onDismiss = { openDetailDialog.value = false })
    }
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2F2D2D)),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(5.dp, 4.dp)
            .clickable(onClick = { openDetailDialog.value = true })
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            AsyncImage(
                model = food.thumbnail,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .clip(RoundedCornerShape(12.dp))
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(0.8f)
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
                Image(
                    painter = painterResource(R.drawable.add_bold_32px),
                    contentDescription = null,
                    modifier = Modifier
                        .size(32.dp)
                        .weight(0.2f)
                        .clickable { onAddToCart() }
                )
            }
        }
    }
}