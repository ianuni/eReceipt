package com.example.ereceipt.adapter

import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.ereceipt.Model.Invoice
import com.example.ereceipt.databinding.ItemViewInvoiceBinding

class ViewInvoiceViewHolder(view:View): RecyclerView.ViewHolder(view) {
    val binding = ItemViewInvoiceBinding.bind(view)

    fun render(invoiceModel: Invoice, ifClick:(Invoice) -> Unit){
        binding.viewInvInfoSeller.text = invoiceModel.getSellerNif()
        binding.viewInvInfoBuyer.text = invoiceModel.getBuyerNif()
        binding.viewInvInfoTotal.text = invoiceModel.getTotal().toString()
        //itemView.setOnClickListener { ifClick(invoiceModel) }
        binding.backgroundViewInvoice.setOnClickListener { ifClick(invoiceModel) }

    }

}