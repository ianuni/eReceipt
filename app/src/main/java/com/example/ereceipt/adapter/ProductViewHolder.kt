package com.example.ereceipt.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.ereceipt.Model.Product
import com.example.ereceipt.databinding.ItemProductBinding


class ProductViewHolder(view: View) : ViewHolder(view) {

    private val binding = ItemProductBinding.bind(view)


    fun render(product: Product, onClickDelete: (Int) -> Unit){
        binding.productName.text = product.name
        binding.productAmount.text = product.amount.toString()
        binding.productPrice.text = product.price.toString()

        binding.deleteBtn.setOnClickListener {
            onClickDelete(adapterPosition)
        }
    }
}