package com.example.ereceipt.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ereceipt.Model.Product
import com.example.ereceipt.R

class InvoiceProductAdapter(
    private val products : ArrayList<Product>) : RecyclerView.Adapter<InvoiceProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InvoiceProductViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return InvoiceProductViewHolder(layoutInflater.inflate(R.layout.item_invoice_product, parent, false))
    }

    override fun onBindViewHolder(holder: InvoiceProductViewHolder, position: Int) {
        val item = products[position]
        holder.render(item)
    }

    override fun getItemCount(): Int {
        return products.size
    }


}