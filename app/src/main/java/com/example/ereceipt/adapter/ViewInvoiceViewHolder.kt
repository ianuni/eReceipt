package com.example.ereceipt.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.ereceipt.Model.Invoice
import com.example.ereceipt.databinding.ItemViewInvoiceBinding

class ViewInvoiceViewHolder(view:View): RecyclerView.ViewHolder(view) {
    val binding = ItemViewInvoiceBinding.bind(view)

    fun render(invoiceModel: Invoice){
        binding.viewInvInfoSeller.text = invoiceModel.getSellerNif()
        binding.viewInvInfoBuyer.text = invoiceModel.getBuyerNif()
        binding.viewInvInfoDate.text = invoiceModel.getCreationDate().toString()
        binding.viewInvInfoTotal.text = invoiceModel.getTotal().toString()

    }
}