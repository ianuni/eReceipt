package com.example.ereceipt.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ereceipt.Model.Company
import com.example.ereceipt.Model.Invoice
import com.example.ereceipt.Model.Notification
import com.example.ereceipt.R

class NotificationAdapter(
    private val notifications : ArrayList<Invoice>,
    private val companies: Map<String, Company>,
    private val onClickDecline:(Int) -> Unit,
    private val onClickAccept:(Int) -> Unit
) : RecyclerView.Adapter<NotificationViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return NotificationViewHolder(layoutInflater.inflate(R.layout.item_notification, parent, false), companies)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        val item = notifications[position]
        holder.render(item, onClickDecline, onClickAccept)
    }

    override fun getItemCount(): Int {
        return notifications.size
    }


}