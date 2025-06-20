package com.example.fitchef.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.fitchef.data.model.FoodBasket

@Database(entities = [FoodBasket::class], version = 1)
abstract class FoodsBasketDB :RoomDatabase() {

    abstract fun foodBasketDAOInterface():FoodsBasketDAO

}