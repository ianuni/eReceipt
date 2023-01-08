package com.example.ereceipt.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.ereceipt.Model.Product
import com.example.ereceipt.databinding.ItemInvoiceProductBinding
import com.example.ereceipt.databinding.ItemProductBinding


class InvoiceProductViewHolder(view: View) : ViewHolder(view) {

    private val binding = ItemInvoiceProductBinding.bind(view)


    fun render(product: Product){
        binding.productName.text = product.name
        binding.productAmount.text = product.amount.toString()
        binding.productPrice.text = product.price.toString()


    }
}