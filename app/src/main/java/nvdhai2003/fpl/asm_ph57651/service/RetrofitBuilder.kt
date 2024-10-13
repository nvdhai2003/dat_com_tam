package nvdhai2003.fpl.asm_ph57651.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    private const val BASE_URL = "https://comtam.phuocsangbn.workers.dev/"

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val authService: AuthService = getRetrofit().create(AuthService::class.java)
    val foodService: FoodService by lazy {
        getRetrofit().create(FoodService::class.java)
    }
}