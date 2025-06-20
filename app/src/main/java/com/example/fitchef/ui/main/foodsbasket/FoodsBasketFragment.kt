package com.example.fitchef.ui.main.foodsbasket

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.fitchef.R
import com.example.fitchef.databinding.FragmentFoodsBasketBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FoodsBasketFragment : Fragment() {

    private var _binding: FragmentFoodsBasketBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<FoodsBasketFragmentViewModel>()

    private val foodsBasketAdapter by lazy {
        FoodsBasketAdapter(
            onRemoveBasketClick = { viewModel.deleteFoodFromBasket(it) }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFoodsBasketBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getFoodsBasket()

        with(binding) {
            foodsBasketRecycleView.setHasFixedSize(true)

            goToPayButton.setOnClickListener {
                findNavController().navigate(R.id.action_foodsBasketFragment_to_paymentFragment)
            }
        }

        initObservers()

    }

    private fun initObservers() = with(binding) {

        viewModel.foodsBasketState.observe(viewLifecycleOwner) { state ->

            foodsLoadingView.isVisible = state.isLoading

            foodsBasketRecycleView.isVisible = state.foodsList != null
            emptyBasketText.isVisible = state.foodsList == null
            goToPayButton.isEnabled = state.foodsList != null

            state.foodsList?.let {
                foodsBasketAdapter.updateList(it)
                foodsBasketRecycleView.adapter = foodsBasketAdapter
            }

            state.errorMessage?.let {
                emptyBasketText.text = it
            }

            state.failMessage?.let {
                emptyBasketText.text = it
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}