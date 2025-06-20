package com.example.fitchef.ui.main.payment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fitchef.common.Resource
import com.example.fitchef.data.model.FoodBasket
import com.example.fitchef.data.repos.FoodsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PaymentFragmentViewModel @Inject constructor(
    private val foodsRepo : FoodsRepository
) : ViewModel() {

    private var _foodsBasket = MutableLiveData<List<FoodBasket>>()
    val foodsBasket: LiveData<List<FoodBasket>>
        get() = _foodsBasket

    init {
        getFoodsBasket()
    }

    private fun getFoodsBasket() {
        val a = foodsRepo.foodsBasket()
        when (a) {
            is Resource.Fail -> {}
            is Resource.Error -> {}
            is Resource.Success<List<FoodBasket>> -> {
                _foodsBasket.value = a.data
            }
        }
    }

    fun clearBasket() = foodsRepo.clearBasket()

}