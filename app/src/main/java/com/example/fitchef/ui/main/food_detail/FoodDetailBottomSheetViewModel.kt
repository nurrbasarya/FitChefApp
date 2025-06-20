package com.example.fitchef.ui.main.food_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fitchef.data.model.Food
import com.example.fitchef.data.repos.FoodsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FoodDetailBottomSheetViewModel @Inject constructor(
    private val foodsRepo: FoodsRepository,
) : ViewModel() {

    private var _isFoodAddedBasket = MutableLiveData<Boolean>()
    val isFoodAddedBasket: LiveData<Boolean>
        get() = _isFoodAddedBasket

    fun addFoodToBasket(food: Food) = foodsRepo.addFoodToBasket(food)
}