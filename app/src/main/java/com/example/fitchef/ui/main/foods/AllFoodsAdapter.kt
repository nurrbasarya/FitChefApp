package com.example.fitchef.ui.main.foods

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.fitchef.data.model.Food
import com.example.fitchef.databinding.FoodItemBinding
import com.squareup.picasso.Picasso
import kotlin.collections.addAll
import kotlin.text.clear

class AllFoodsAdapter(
    private val onAddBasketClick: (Food) -> Unit,
    private val onFoodClick: (Food) -> Unit,
) : RecyclerView.Adapter<AllFoodsAdapter.FoodItemDesign>(), Filterable{

    private val foodsList =ArrayList<Food>()
    var foodsFilterList = ArrayList<Food>()

    init {
        foodsFilterList = foodsList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodItemDesign {
        val binding = FoodItemBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return FoodItemDesign(binding)
    }

    override fun onBindViewHolder(holder: FoodItemDesign, position: Int) =
        holder.bind(foodsFilterList[position])

    inner class FoodItemDesign(private var binding: FoodItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

        fun bind(food: Food) {

            with(binding) {
                foodContentText.text = food.description
                foodNameText.text = food.name
                foodPriceText.text = "${food.price} ₺"

                food.imageUrl?.let {
                    Picasso.get().load(it).into(foodImageView)
                }

                foodImageView.setOnClickListener{
                    onFoodClick(food)
                }

                addBasketImage.setOnClickListener {
                    onAddBasketClick(food)
                }
            }
        }
    }

    override fun getItemCount(): Int =foodsFilterList.size

    fun updateList(list: List<Food>) {
        foodsList.clear()
        foodsList.addAll(list)
        foodsFilterList = ArrayList(foodsList) // foodsFilterList'i de güncelleyin ve notifyDataSetChanged() çağırın
        notifyDataSetChanged() // Tamamen farklı bir liste geldiğinde notifyDataSetChanged() kullanın
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {

                val searchText = constraint.toString().lowercase()
                foodsFilterList = if (searchText.isEmpty()) {
                    foodsList
                } else {
                    val resultList = ArrayList<Food>()
                    for (row in foodsList) {
                        row.name?.let { foodName ->
                            row.description?.let { foodContent ->
                                if (foodName.lowercase().contains(searchText) ||
                                    foodContent.lowercase().contains(searchText)
                                ) {
                                    resultList.add(row)
                                }
                            }
                        }
                    }
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = foodsFilterList
                return filterResults
            }
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                foodsFilterList = results?.values as ArrayList<Food>
                notifyDataSetChanged() // Filtreleme sonrası listenin yapısı değişebileceği için notifyDataSetChanged() kullanın
            }
        }
    }
}