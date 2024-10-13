package nvdhai2003.fpl.asm_ph57651.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import nvdhai2003.fpl.asm_ph57651.model.CartItem
import nvdhai2003.fpl.asm_ph57651.viewmodel.CartViewModel


@Composable
fun CartScreen(viewModel: CartViewModel) {
    val cartItems = viewModel.getCartItems()

    Column {
        LazyColumn(
            state = rememberLazyListState(),
            modifier = Modifier.fillMaxSize().background(color = Color(0xFF252121))
        ) {
            items(cartItems.size) { cartItem ->
                CartItemRow(
                    cartItem = cartItems[cartItem],
                    onIncrease = { viewModel.increaseQuantity(cartItems[cartItem]) },
                    onDecrease = { viewModel.decreaseQuantity(cartItems[cartItem]) }
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Tổng số tiền: ${viewModel.calculateTotal()} VND",
            color = Color.White,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
fun CartItemRow(cartItem: CartItem, onIncrease: () -> Unit, onDecrease: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberImagePainter(cartItem.food.thumbnail),
            contentDescription = null,
            modifier = Modifier.size(64.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(text = cartItem.food.name, style = MaterialTheme.typography.bodyMedium, color = Color.White)
            Text(text = "${cartItem.food.price} VND", style = MaterialTheme.typography.bodyMedium, color = Color.White)
        }
        Row {
            IconButton(onClick = onDecrease) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Decrease")
            }
            Text(text = "${cartItem.quantity}", modifier = Modifier.padding(8.dp), color = Color.White)
            IconButton(onClick = onIncrease) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Increase")
            }
        }
    }
}











