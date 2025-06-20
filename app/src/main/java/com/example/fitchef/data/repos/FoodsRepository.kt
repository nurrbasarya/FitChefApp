package com.example.fitchef.data.repos

import com.example.fitchef.common.Resource
import com.example.fitchef.data.model.Food
import com.example.fitchef.data.model.FoodBasket
import com.example.fitchef.data.retrofit.FoodsService
import com.example.fitchef.data.room.FoodsBasketDAO

class FoodsRepository (
    private val foodsService: FoodsService,
    private val foodsDAO: FoodsBasketDAO
) {

    suspend fun foods():Resource<List<Food>> {
        return try {
            val response = foodsService.allFoods()

            if (response.isSuccessful) {
                Resource.Success(response.body()?.foods.orEmpty())
            } else {
                Resource.Fail(response.message())
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    fun foodsBasket(): Resource<List<FoodBasket>> {
        return try {
            val response = foodsDAO.getFoodsBasket()

            if (response.isNullOrEmpty()) {
                Resource.Fail("Sepetinizde Ürün Bulunmamaktadır.")
            } else {
                Resource.Success(response)
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    fun addFoodToBasket(food: Food) {
        val foodBasket =FoodBasket(
            name = food.name,
            content = food.description,
            sampleMenu = food.sampleMenu,
            price = food.price,
            imageUrl = food.imageUrl
        )

        foodsDAO.addFoodBasket(foodBasket)
    }

    fun deleteFoodFromBasket(foodId: Int) = foodsDAO.deleteFoodWithId(foodId)

    fun clearBasket() = foodsDAO.clearBasket()

}