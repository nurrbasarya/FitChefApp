package com.example.fitchef.ui.main.foods

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.fitchef.common.hideKeyboard
import com.example.fitchef.common.setViewsGone
import com.example.fitchef.common.setViewsVisible
import com.example.fitchef.common.showSnackBar
import com.example.fitchef.databinding.FragmentFoodsBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FoodsFragment : Fragment() {

    private var _binding:  FragmentFoodsBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<FoodsViewModel>()

    private val bestSellersAdapter by lazy {
        BestSellersAdapter(
            onFoodClick =  {
                val action = FoodsFragmentDirections.actionFoodsFragmentToFoodDetailBottomSheet(it)
                findNavController().navigate(action)
            }
        )
    }

    private val allFoodsAdapter by lazy {
        AllFoodsAdapter(
            onAddBasketClick = { food ->
                viewModel.addFoodToBasket(food)
                view?.let {
                    Snackbar.make(it, "${food.name} sepete eklendi", Snackbar.LENGTH_SHORT).show()
                }
            },
            onFoodClick =  { food ->
                val action = FoodsFragmentDirections.actionFoodsFragmentToFoodDetailBottomSheet(food)
                findNavController().navigate(action)
            }
        )
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFoodsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getFoods()

        with(binding) {
            // Set the adapters here, only once
            allFoodsRecycler.setHasFixedSize(true)
            allFoodsRecycler.adapter = allFoodsAdapter

            bestSellersRecycler.setHasFixedSize(true) // You were missing setting adapter for bestSellersRecycler as well
            bestSellersRecycler.adapter = bestSellersAdapter

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {

                    allFoodsAdapter.filter.filter(newText)

                    if (newText.isNullOrEmpty().not()) {
                        setViewsGone(bestSellersTitle,bestSellersRecycler)
                    } else {
                        setViewsVisible(bestSellersTitle,bestSellersRecycler)
                        hideKeyboard(requireActivity(), view)
                    }
                    return false
                }
            })
        }

        initObserver()
    }

    private fun initObserver() = with(binding) {

        viewModel.foodsState.observe(viewLifecycleOwner) { state ->

            foodsLoadingView.isVisible = state.isLoading

            state.foodsList?.let { foodList ->
                println("1")
                println(foodList.size)
                allFoodsAdapter.updateList(foodList)
                // Remove this line: allFoodsRecycler.adapter = allFoodsAdapter
            }

            state.bestSellersList?.let {  bestSellerList ->
                println("2")
                bestSellersAdapter.updateList(bestSellerList)
                // Remove this line if it existed for bestSellersRecycler
            }

            state.errorMessage?.let {
                println("3")
                requireView().showSnackBar(it)
            }

            state.failMessage?.let {
                println("4")
                println(it)
                requireView().showSnackBar(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}