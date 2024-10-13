package nvdhai2003.fpl.asm_ph57651.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import nvdhai2003.fpl.asm_ph57651.model.Food
import nvdhai2003.fpl.asm_ph57651.service.RetrofitBuilder
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody

class FoodViewModel : ViewModel() {
    private val _foods = MutableLiveData<List<Food>>()
    val foods: LiveData<List<Food>> = _foods
    private var originalFoodsList: List<Food> = emptyList()

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        getFoods()
    }

    private fun getFoods() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = RetrofitBuilder.foodService.getListFoods()
                Log.d("GET FOOD", "Foods: $response")
                if (response.isSuccessful) {
                    val foodList = response.body()?.map { it.toFood() } ?: emptyList()
                    _foods.postValue(foodList)
                    originalFoodsList = foodList
                } else {
                    _foods.postValue(emptyList())
                }
            } catch (e: Exception) {
                Log.e("TAG", "getFoods: " + e.message)
                _foods.postValue(emptyList())
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteFood(id: Int, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = RetrofitBuilder.foodService.deleteFood(id)
                Log.d("DELETE FOOD", "Delete food: $response")
                if (response.isSuccessful) {
                    getFoods()
                    onSuccess()
                } else {
                    onError("Xóa món ăn thất bại: ${response.message()}")
                    Log.e("Delete Food", "Xóa món ăn thất bại: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("TAG", "deleteFood: " + e.message)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun searchFood(query: String) {
        if (query.isEmpty()) {
            getFoods()
        } else {
            viewModelScope.launch {
                try {
                    val response = RetrofitBuilder.foodService.searchFood(query)
                    Log.d("SEARCH_API", "Response: ${response.body()}")
                    if (response.isSuccessful) {
                        _foods.postValue(response.body()?.map { it.toFood() })
                    } else {
                        _foods.postValue(emptyList())
                    }
                } catch (e: Exception) {
                    Log.e("TAG", "searchFood: " + e.message)
                    _foods.postValue(emptyList())
                }
            }
        }
    }

    fun addFood(
        name: String,
        price: Int,
        thumbnail: String,
        description: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val namePart = RequestBody.create("text/plain".toMediaTypeOrNull(), name)
                val pricePart =
                    RequestBody.create("text/plain".toMediaTypeOrNull(), price.toString())
                val thumbnailPart = RequestBody.create("text/plain".toMediaTypeOrNull(), thumbnail)
                val descriptionPart =
                    RequestBody.create("text/plain".toMediaTypeOrNull(), description)

                val response = RetrofitBuilder.foodService.createFood(
                    namePart,
                    pricePart,
                    thumbnailPart,
                    descriptionPart
                )
                if (response.isSuccessful) {
                    getFoods()
                    onSuccess()
                } else {
                    onError("Thêm món ăn thất bại: ${response.message()}")
                    Log.e("Add Food", "Thêm món ăn thất bại: ${response.message()}")
                }
            } catch (e: Exception) {
                onError("Lỗi khi thêm món ăn: ${e.message}")
                Log.e("TAG", "addFood: " + e.message)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updateFood(
        id: Int,
        name: String,
        price: Int,
        thumbnail: String,
        description: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val idPart = RequestBody.create("text/plain".toMediaTypeOrNull(), id.toString())
                val namePart = RequestBody.create("text/plain".toMediaTypeOrNull(), name)
                val pricePart =
                    RequestBody.create("text/plain".toMediaTypeOrNull(), price.toString())
                val thumbnailPart = RequestBody.create("text/plain".toMediaTypeOrNull(), thumbnail)
                val descriptionPart =
                    RequestBody.create("text/plain".toMediaTypeOrNull(), description)

                val response = RetrofitBuilder.foodService.updateFood(
                    idPart,
                    namePart,
                    pricePart,
                    thumbnailPart,
                    descriptionPart
                )
                if (response.isSuccessful) {
                    getFoods()
                    onSuccess()
                } else {
                    onError("Failed to update food: ${response.message()}")
                }
            } catch (e: Exception) {
                onError("Error updating food: ${e.message}")
                Log.e("UPDATE FOOD", "Error updating food: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getFoodById(id: Int?): Food? {
        return originalFoodsList.find { it.id == id }
    }
}
