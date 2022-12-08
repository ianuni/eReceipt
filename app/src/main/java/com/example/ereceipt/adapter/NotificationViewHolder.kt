package com.example.ereceipt.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.ereceipt.Model.Notification
import com.example.ereceipt.databinding.ItemNotificationBinding

class NotificationViewHolder(view: View) :  ViewHolder(view){
    private val binding = ItemNotificationBinding.bind(view)

    fun render(notification: Notification){
        binding.sellerName.text = notification.sellerName
        binding.invoicePrice.text = notification.price.toString()
    }
}