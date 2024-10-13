package nvdhai2003.fpl.asm_ph57651.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import nvdhai2003.fpl.asm_ph57651.viewmodel.FoodViewModel

@Composable
fun FoodDetailScreen(foodViewModel: FoodViewModel, foodId: Int?) {
    val food = foodViewModel.getFoodById(foodId)

    food?.let {
        Column {
            Text(text = "Tên món ăn: ${it.name}")
            Text(text = "Giá: ${it.price}")
            Text(text = "Mô tả: ${it.description}")
        }
    } ?: run {
        Text("Không tìm thấy thông tin món ăn")
    }
}
