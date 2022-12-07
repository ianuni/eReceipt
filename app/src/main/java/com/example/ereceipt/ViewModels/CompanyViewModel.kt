package com.example.ereceipt.ViewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ereceipt.Model.Company
import com.example.ereceipt.Model.Invoice

class CompanyViewModel: ViewModel() {
    val company = MutableLiveData<Company>()
    val checkedInvoices = MutableLiveData<MutableList<Invoice>>()
    val nonCheckedInvoices = MutableLiveData<MutableList<Invoice>>()

    fun setCompany(company: Company){
        this.company.value = company
    }

    fun setInvoices(invoices: Collection<Invoice>){
        checkedInvoices.value = mutableListOf()
        nonCheckedInvoices.value = mutableListOf()
        for (invoice in invoices){
            //if (!invoice.verification && invoice.buyerNif.equals(company.value!!.nif)) {
            if (!invoice.getVerification() && invoice.getBuyerNif().equals(company.value!!.nif)) {
                nonCheckedInvoices.value!!.add(invoice)
                Log.e("X", "nonChecked: "+ invoice.toString())
            } else {
                checkedInvoices.value!!.add(invoice)
                Log.e("Y", "checked: "+ invoice.toString())
            }
        }
    }

    fun updateInvoices(){
        for (invoice in nonCheckedInvoices.value!!){
            if (invoice.getVerification() && invoice.getBuyerNif().equals(company.value!!.nif)){
            //if (invoice.verification && invoice.buyerNif.equals(company.value!!.nif)){
                checkedInvoices.value!!.add(invoice)
                nonCheckedInvoices.value!!.remove(invoice)
            }
        }
    }
}