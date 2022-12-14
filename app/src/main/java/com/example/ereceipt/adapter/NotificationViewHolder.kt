package com.example.ereceipt.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.ereceipt.Model.Company
import com.example.ereceipt.Model.Invoice
import com.example.ereceipt.Model.Notification
import com.example.ereceipt.databinding.ItemNotificationBinding

class NotificationViewHolder(view: View, private val companies: Map<String, Company>) :  ViewHolder(view){
    private val binding = ItemNotificationBinding.bind(view)

    fun render(
        notification: Invoice,
        onClickDecline:(Int) -> Unit,
        onClickAccept:(Int) -> Unit) {
        if (companies.containsKey(notification.getSellerNif())){
            binding.sellerName.text = companies[notification.getSellerNif()]!!.name
        }
        else{
            binding.sellerName.text = notification.getSellerNif()
        }
        binding.invoicePrice.text = notification.getTotal().toString()

        binding.declineBtn.setOnClickListener {
            onClickDecline(adapterPosition)
        }

        binding.acceptBtn.setOnClickListener {
            onClickAccept(adapterPosition)
        }
    }
}