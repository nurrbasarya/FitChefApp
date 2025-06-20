package com.example.fitchef.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "foodsbasketdatabase")
data class FoodBasket(

    @PrimaryKey(autoGenerate = true)

    @ColumnInfo(name = "id")
    val id: Int =0,

    @ColumnInfo(name = "name")
    val name: String?,

    @ColumnInfo(name = "content")
    val content: String?,

    @ColumnInfo(name = "sampleMenu")
    val sampleMenu: String?,

    @ColumnInfo(name = "price")
    val price: Float?,

    @ColumnInfo(name = "imageUrl")
    val imageUrl: String?,

)
