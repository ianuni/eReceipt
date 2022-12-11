package com.example.ereceipt.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ereceipt.Model.Invoice
import com.example.ereceipt.R

class ViewInvoiceAdapter (
    private val invoiceList:List<Invoice>
    ) : RecyclerView.Adapter<ViewInvoiceViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewInvoiceViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewInvoiceViewHolder(layoutInflater.inflate(R.layout.item_view_invoice, parent, false))

    }

    override fun onBindViewHolder(holder: ViewInvoiceViewHolder, position: Int) {
        val item = invoiceList[position]
        holder.render(item)
    }

    override fun getItemCount(): Int = invoiceList.size
}