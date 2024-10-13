package nvdhai2003.fpl.asm_ph57651.service

import nvdhai2003.fpl.asm_ph57651.model.FoodResponse
import okhttp3.Request
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface FoodService {
    @GET("foods")
    suspend fun getListFoods() : Response<List<FoodResponse>>

    @GET("deleteFood")
    suspend fun deleteFood(@Query("id") id: Int): Response<Unit>

    @Multipart
    @POST("/createFood")
    suspend fun createFood(
        @Part("name") name: RequestBody,
        @Part("price") price: RequestBody,
        @Part("thumbnail") thumbnail: RequestBody,
        @Part("description") description: RequestBody
    ): Response<FoodResponse>

    @Multipart
    @POST("/updateFood")
    suspend fun updateFood(
        @Part("id") id: RequestBody,
        @Part("name") name: RequestBody,
        @Part("price") price: RequestBody,
        @Part("thumbnail") thumbnail: RequestBody,
        @Part("description") description: RequestBody
    ): Response<FoodResponse>

    @FormUrlEncoded
    @POST("searchFood")
    suspend fun searchFood(
        @Field("text") query: String
    ): Response<List<FoodResponse>>
}
