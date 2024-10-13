package nvdhai2003.fpl.asm_ph57651.service

import nvdhai2003.fpl.asm_ph57651.model.Food
import nvdhai2003.fpl.asm_ph57651.model.FoodResponse

class Transform {
    fun FoodResponse.toFood(): Food {
        return Food(
            id = this.foodId,
            name = this.foodName,
            price = this.foodPrice,
            thumbnail = this.foodThumbnail,
            description = this.foodDescription
        )
    }
}

