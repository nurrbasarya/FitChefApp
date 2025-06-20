package com.example.fitchef.data.model

import com.google.gson.annotations.SerializedName

data class FoodsResponse(
    @SerializedName("data") var foods: List<Food>?,
    @SerializedName("success") var success: Int?,
)
