package com.example.fitchef.ui.main.foods

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fitchef.data.model.Food
import com.example.fitchef.databinding.BestSellerItemBinding
import com.squareup.picasso.Picasso

class BestSellersAdapter(
    private val onFoodClick: (Food) -> Unit,
) : RecyclerView.Adapter<BestSellersAdapter.BestSellerItemDesign>() {

    private val bestSellersList = ArrayList<Food>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestSellerItemDesign {
        val binding =
            BestSellerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BestSellerItemDesign(binding)
    }

    override fun onBindViewHolder(holder: BestSellerItemDesign, position: Int) {
        holder.bind(bestSellersList[position])
    }

    inner class BestSellerItemDesign(private var binding:BestSellerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(food: Food) {

            with(binding) {
                foodContentText.text = food.description
                foodNameText.text = food.name
                foodPriceText.text = "${food.price} ₺"

                food.imageUrl.let {
                    Picasso.get().load(it).into(foodImageView)
                }

                foodImageView.setOnClickListener {
                    onFoodClick(food)
                }
            }
        }
    }

    override fun getItemCount(): Int = bestSellersList.size

    fun updateList(list: List<Food>){
        bestSellersList.clear()
        bestSellersList.addAll(list)
        notifyItemRangeInserted(0, list.size)
    }
}