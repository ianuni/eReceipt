package com.example.ereceipt.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.ereceipt.Model.Company
import com.example.ereceipt.Model.Invoice
import com.example.ereceipt.R
import com.example.ereceipt.databinding.ItemInvoiceBinding
import com.example.ereceipt.databinding.ItemViewInvoiceBinding

class ViewInvoiceViewHolder(view:View, private val companies: Map<String, Company>): RecyclerView.ViewHolder(view) {
    val binding = ItemInvoiceBinding.bind(view)

    fun render(invoiceModel: Invoice, ifClick:(Invoice) -> Unit){

        if(companies.containsKey(invoiceModel.getSellerNif())){
            binding.sellerValue.text = companies[invoiceModel.getSellerNif()]!!.name
        }
        else{
            binding.sellerValue.text = invoiceModel.getSellerNif()
        }
        if(companies.containsKey(invoiceModel.getBuyerNif())) {
            binding.buyerValue.text = companies[invoiceModel.getBuyerNif()]!!.name
        }
        else{
            binding.buyerValue.text = invoiceModel.getBuyerNif()
        }

        binding.totalValue.text = invoiceModel.getTotal().toString()

        if (invoiceModel.getVerification()){
            binding.stateValue.text = "Accepted"
            binding.stateValue.setTextColor(Color.parseColor("#088DEE"))
            binding.stateIcon.setBackgroundResource(R.drawable.circle_check_regular)
            binding.stateIcon.background.setTint(Color.parseColor("#088DEE"))
        }
        else{
            if(invoiceModel.getBuyerView()){
                binding.stateValue.text = "Waiting"
                binding.stateValue.setTextColor(Color.parseColor("#ee9208"))
                binding.stateIcon.setBackgroundResource(R.drawable.clock_regular)
                binding.stateIcon.background.setTint(Color.parseColor("#ee9208"))
            }
            else{
                binding.stateValue.text = "Declined"
                binding.stateValue.setTextColor(Color.parseColor("#B80C0C"))
                binding.stateIcon.setBackgroundResource(R.drawable.circle_xmark_regular)
                binding.stateIcon.background.setTint(Color.parseColor("#B80C0C"))
            }
        }
        //itemView.setOnClickListener { ifClick(invoiceModel) }
        binding.background.setOnClickListener { ifClick(invoiceModel) }

    }

}