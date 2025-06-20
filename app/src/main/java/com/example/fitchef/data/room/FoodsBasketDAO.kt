package com.example.fitchef.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fitchef.data.model.FoodBasket

@Dao
interface FoodsBasketDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addFoodBasket(foodBasket: FoodBasket)

    @Query("SELECT * FROM foodsbasketdatabase")
    fun getFoodsBasket(): List<FoodBasket>?

    @Query("DELETE FROM foodsbasketdatabase WHERE id = :idInput")
    fun deleteFoodWithId(idInput: Int)

    @Query("DELETE FROM foodsbasketdatabase")
    fun clearBasket()
}