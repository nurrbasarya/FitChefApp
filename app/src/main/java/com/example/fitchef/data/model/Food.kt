package com.example.fitchef.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Food(
    val id: Int? =0,
    val name: String?,
    val description: String?,
    val sampleMenu:String?,
    val price: Float?,
    val imageUrl: String?,
) : Parcelable
