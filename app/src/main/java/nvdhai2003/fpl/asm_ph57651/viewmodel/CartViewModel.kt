package nvdhai2003.fpl.asm_ph57651.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import nvdhai2003.fpl.asm_ph57651.model.CartItem
import nvdhai2003.fpl.asm_ph57651.model.Food

class CartViewModel : ViewModel() {

    private val _cartItems = mutableStateListOf<CartItem>()


    fun getCartItems(): List<CartItem> {
        return _cartItems
    }


    fun addToCart(food: Food) {
        val existingItem = _cartItems.find { it.food.id == food.id }
        if (existingItem != null) {
            existingItem.quantity++
            Log.d("CartViewModel", "Increased quantity of ${food.name} to ${existingItem.quantity}")
        } else {
            _cartItems.add(CartItem(food, 1))
            Log.d("CartViewModel", "Added ${food.name} to cart with quantity 1")
        }


        Log.d("CartViewModel", "Current cart size: ${_cartItems.size}")


        val total = calculateTotal()
        Log.d("CartViewModel", "Total price calculated: $total")
    }



    fun increaseQuantity(cartItem: CartItem) {
        cartItem.quantity++
        Log.d("CartViewModel", "Increased quantity of ${cartItem.food.name} to ${cartItem.quantity}")
    }


    fun decreaseQuantity(cartItem: CartItem) {
        if (cartItem.quantity > 1) {
            cartItem.quantity--
            Log.d("CartViewModel", "Decreased quantity of ${cartItem.food.name} to ${cartItem.quantity}")
        } else {
            _cartItems.remove(cartItem)
            Log.d("CartViewModel", "Removed ${cartItem.food.name} from cart")
        }
    }


    fun calculateTotal(): Int {
        val total = _cartItems.sumOf { it.food.price * it.quantity }
        Log.d("CartViewModel", "Total price calculated: $total")
        return total
    }
}
