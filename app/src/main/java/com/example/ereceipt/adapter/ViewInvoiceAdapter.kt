package com.example.ereceipt.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ereceipt.Model.Company
import com.example.ereceipt.Model.Invoice
import com.example.ereceipt.R

class ViewInvoiceAdapter (
    private var invoiceList:List<Invoice>,
    private val companies: Map<String, Company>,
    private val ifClick:(Invoice) -> Unit,
    private val onClickDelete:(Int) -> Unit
    ) : RecyclerView.Adapter<ViewInvoiceViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewInvoiceViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewInvoiceViewHolder(layoutInflater.inflate(R.layout.item_invoice, parent, false), companies)

    }

    override fun onBindViewHolder(holder: ViewInvoiceViewHolder, position: Int) {
        val item = invoiceList[position]
        holder.render(item, ifClick, onClickDelete)
    }

    override fun getItemCount(): Int = invoiceList.size

    fun updateInvoices(invoiceList:List<Invoice>) {
        this.invoiceList = invoiceList
        notifyDataSetChanged()
    }
}