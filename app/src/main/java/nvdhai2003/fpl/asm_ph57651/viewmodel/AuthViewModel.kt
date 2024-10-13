package nvdhai2003.fpl.asm_ph57651.viewmodel

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import nvdhai2003.fpl.asm_ph57651.model.User
import nvdhai2003.fpl.asm_ph57651.service.RetrofitBuilder
import retrofit2.awaitResponse

class AuthViewModel(private val context: Context): ViewModel() {
    var errorMessage: String? = null
    var successMessage: String? = null

    private val _isLoading = MutableStateFlow<Boolean>(false) // Trạng thái đang xử lý
    val isLoading: StateFlow<Boolean> = _isLoading

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("login_prefs", Context.MODE_PRIVATE)

    private fun saveLoginState(username: String) {
        sharedPreferences.edit().putString("logged_in_user", username).apply()
    }

    fun isUserLoggedIn(): Boolean {
        return sharedPreferences.getString("logged_in_user", null) != null
    }


    fun isAdminUser(): Boolean {
        val username = sharedPreferences.getString("logged_in_user", null)
        return username == "admin"
    }

    fun signup(username: String, password: String, fullname: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = RetrofitBuilder.authService.signup(username, password, fullname).awaitResponse()
                if (response.isSuccessful) {
                    Log.d("Signup", "Đăng ký thành công: ${response.message()}")
                    onSuccess()
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("Signup", "Đăng ký thất bại: ${response.message()} - Error body: $errorBody")
                    onError("Đăng ký thất bại: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("Signup", "Lỗi kết nối: ${e.localizedMessage}")
                onError("Lỗi kết nối: ${e.localizedMessage}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun login(username: String, password: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = RetrofitBuilder.authService.login(username, password).awaitResponse()
                if (username == "admin" && password == "123abc") {
                    Log.d("Login", "Đăng nhập với tài khoản admin")
                    onSuccess()
                } else if (response.isSuccessful) {
                    saveLoginState(username)
                    Log.d("Login", "Đăng nhập thành công: ${response.message()}")
                    onSuccess()
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("Login", "Đăng nhập thất bại: ${response.message()} - Error body: $errorBody")
                    onError("Đăng nhập thất bại: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("Login", "Lỗi kết nối: ${e.localizedMessage}")
                onError("Lỗi kết nối: ${e.localizedMessage}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun logout(onSuccess: () -> Unit, onError: (String) -> Unit) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                sharedPreferences.edit().remove("logged_in_user").apply()
                Log.d("Logout", "Đăng xuất thành công")
                onSuccess()
            } catch (e: Exception) {
                Log.e("Logout", "Lỗi đăng xuất: ${e.localizedMessage}")
                onError("Lỗi đăng xuất: ${e.localizedMessage}")
            } finally {
                _isLoading.value = false
            }
        }
    }

}