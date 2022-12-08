package com.example.ereceipt.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ereceipt.Model.Product
import com.example.ereceipt.R

class ProductAdapter(
    private val products : ArrayList<Product>,
    private val onClickDelete:(Int) -> Unit
) : RecyclerView.Adapter<ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ProductViewHolder(layoutInflater.inflate(R.layout.item_product, parent, false))
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item = products[position]
        holder.render(item, onClickDelete )
    }

    override fun getItemCount(): Int {
        return products.size
    }

}