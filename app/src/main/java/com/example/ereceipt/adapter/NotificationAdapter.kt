package com.example.ereceipt.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ereceipt.Model.Notification
import com.example.ereceipt.R

class NotificationAdapter(private val notifications : ArrayList<Notification>) : RecyclerView.Adapter<NotificationViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return NotificationViewHolder(layoutInflater.inflate(R.layout.item_notification, parent, false))
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        val item = notifications[position]
        holder.render(item)
    }

    override fun getItemCount(): Int {
        return notifications.size
    }


}