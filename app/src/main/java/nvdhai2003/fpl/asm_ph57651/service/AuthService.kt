package nvdhai2003.fpl.asm_ph57651.service

import nvdhai2003.fpl.asm_ph57651.model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthService {

    @FormUrlEncoded
    @POST("signup")
    fun signup(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("fullname") fullname: String
    ): Call<Void>

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<Void>
}