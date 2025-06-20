package com.example.fitchef.ui.main.foodsbasket

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fitchef.data.model.FoodBasket
import com.example.fitchef.databinding.FoodBasketItemBinding
import com.squareup.picasso.Picasso

class FoodsBasketAdapter(
    private val onRemoveBasketClick: (Int) ->Unit
) : RecyclerView.Adapter<FoodsBasketAdapter.FoodBasketItemDesign>() {

    private val foodsBasketList = ArrayList<FoodBasket>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodBasketItemDesign {
        val binding =
            FoodBasketItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodBasketItemDesign(binding)
    }

    override fun onBindViewHolder(holder: FoodBasketItemDesign, position: Int) =
        holder.bind(foodsBasketList[position])

    inner class FoodBasketItemDesign(private var binding:FoodBasketItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(foodBasket: FoodBasket) {

            with(binding) {
                foodNameText.text = foodBasket.name
                foodPriceText.text = "${foodBasket.price} ₺"

                foodBasket.imageUrl?.let {
                    Picasso.get().load(it).into(foodBasketImageView)
                }

                foodBasketDelete.setOnClickListener {
                    onRemoveBasketClick(foodBasket.id)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return foodsBasketList.size
    }

    fun updateList(list: List<FoodBasket>) {
        foodsBasketList.clear()
        foodsBasketList.addAll(list)
        notifyItemRangeInserted(0, list.size)
    }
}



