package com.example.fitchef.ui.main.foodsbasket

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitchef.common.Resource
import com.example.fitchef.data.model.FoodBasket
import com.example.fitchef.data.repos.FoodsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodsBasketFragmentViewModel @Inject constructor(
    private val foodsRepo: FoodsRepository
) : ViewModel() {

    private var _foodsBasketState = MutableLiveData(FoodsBasketState(isLoading = true))
    val foodsBasketState: LiveData<FoodsBasketState>
        get() = _foodsBasketState

    fun getFoodsBasket() {
        viewModelScope.launch {
            when (val response = foodsRepo.foodsBasket()) {
                is Resource.Success -> {
                    _foodsBasketState.value = FoodsBasketState(
                        isLoading = false,
                        foodsList = response.data
                    )
            }

                is Resource.Fail -> {
                    _foodsBasketState.value = FoodsBasketState(isLoading = false, failMessage = response.message)
                }

                is Resource.Error -> {
                    _foodsBasketState.value = FoodsBasketState(isLoading = false, errorMessage = response.throwable.message)
                }

            }
        }
    }

    fun deleteFoodFromBasket(foodId: Int) {
        foodsRepo.deleteFoodFromBasket(foodId)
        getFoodsBasket()
    }
}
data class FoodsBasketState (
    val isLoading: Boolean = false,
    val foodsList: List<FoodBasket>? = null,
    val errorMessage: String? = null,
    val failMessage: String? = null
)
