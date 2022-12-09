package com.example.ereceipt.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.ereceipt.Model.Invoice
import com.example.ereceipt.Model.Notification
import com.example.ereceipt.databinding.ItemNotificationBinding

class NotificationViewHolder(view: View) :  ViewHolder(view){
    private val binding = ItemNotificationBinding.bind(view)

    fun render(
        notification: Invoice,
        onClickDecline:(Int) -> Unit,
        onClickAccept:(Int) -> Unit) {
        binding.sellerName.text = notification.getSellerNif()
        binding.invoicePrice.text = notification.getTotal().toString()

        binding.declineBtn.setOnClickListener {
            onClickDecline(adapterPosition)
        }

        binding.acceptBtn.setOnClickListener {
            onClickAccept(adapterPosition)
        }
    }
}