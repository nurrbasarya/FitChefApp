package com.example.fitchef.ui.main.food_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.fitchef.R
import com.example.fitchef.common.showSnackBar
import com.example.fitchef.databinding.FoodDetailBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FoodDetailBottomSheet : BottomSheetDialogFragment() {

    private var _binding: FoodDetailBottomSheetBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<FoodDetailBottomSheetViewModel>()

    private val args by navArgs<FoodDetailBottomSheetArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FoodDetailBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val food = args.foodModel

        with(binding) {
            foodName.text = food.name
            foodContent.text = food.description
            foodMenu.text =food.sampleMenu
            foodPrice.text = "${food.price} ₺"

            Picasso.get().load(food.imageUrl).into(foodImage)

            addCartButton.setOnClickListener {
                viewModel.addFoodToBasket(food)
                Toast.makeText(requireContext(), "${food.name} sepetinize eklendi", Toast.LENGTH_SHORT).show()
            }

            viewModel.isFoodAddedBasket.observe(viewLifecycleOwner) {
                if (it) dialog?.window!!.decorView.showSnackBar(getString(R.string.add_basket_snack_text))
                else dialog?.window!!.decorView.showSnackBar(getString(R.string.add_food_basket_error))
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}