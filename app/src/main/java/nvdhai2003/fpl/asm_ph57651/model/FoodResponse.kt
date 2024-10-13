package nvdhai2003.fpl.asm_ph57651.model

import com.google.gson.annotations.SerializedName

data class FoodResponse(
    @SerializedName("id") val foodId: Int,
    @SerializedName("name") val foodName: String,
    @SerializedName("price") val foodPrice: Int,
    @SerializedName("thumbnail") val foodThumbnail: String,
    @SerializedName("description") val foodDescription: String,
) {
    fun toFood(): Food {
        return Food(
            id = this.foodId,
            name = this.foodName,
            price = this.foodPrice,
            thumbnail = this.foodThumbnail,
            description = this.foodDescription
        )
    }
}
