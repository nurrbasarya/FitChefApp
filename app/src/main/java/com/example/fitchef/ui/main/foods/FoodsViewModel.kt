package com.example.fitchef.ui.main.foods

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitchef.common.Resource
import com.example.fitchef.data.model.Food
import com.example.fitchef.data.repos.FoodsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodsViewModel @Inject constructor(
    private val foodsRepo: FoodsRepository
) : ViewModel() {

    private var _foodsState =MutableLiveData(FoodsState(isLoading = true))
    val foodsState: LiveData<FoodsState>
        get() = _foodsState

    fun getFoods() {
        viewModelScope.launch {
            when (val response = foodsRepo.foods()) {
                is Resource.Success -> {
                    _foodsState.value = FoodsState(
                        isLoading = false,
                        foodsList = response.data,
                    )
                }

                is Resource.Fail -> {
                    _foodsState.value = FoodsState(isLoading = false, failMessage = response.message)
                }

                is Resource.Error -> {
                    _foodsState.value = FoodsState(isLoading = false, errorMessage = response.throwable.message)
                }
            }
        }
    }

    fun addFoodToBasket(food: Food) =foodsRepo.addFoodToBasket(food)

}

class FoodsState(
    val isLoading: Boolean = false,
    val foodsList: List<Food>? = null,
    val bestSellersList: List<Food>? = null,
    val errorMessage: String? = null,
    val failMessage: String? =null
)
