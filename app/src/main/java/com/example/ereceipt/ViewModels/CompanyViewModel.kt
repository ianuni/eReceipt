package com.example.ereceipt.ViewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ereceipt.Model.Company
import com.example.ereceipt.Model.Invoice

class CompanyViewModel: ViewModel() {
    val company = MutableLiveData<Company>()
    val invoices = MutableLiveData<MutableList<Invoice>>()
    //val nonCheckedInvoices = MutableLiveData<MutableList<Invoice>>()
    val nonCheckedInvoices = ArrayList<Invoice>()
    fun setCompany(company: Company){
        Log.e("a","company set")
        this.company.value = company
    }

    fun setInvoices(invoicesList: Collection<Invoice>){
        invoices.value = mutableListOf()
        //nonCheckedInvoices.value = mutableListOf()
        for (invoice in invoicesList){
            //if (!invoice.verification && invoice.buyerNif.equals(company.value!!.nif)) {
            //Log.e("X", "Invoice =  Verification: "+ invoice.getVerification().toString() + " BuyerNIF: " + invoice.getBuyerNif().toString())
            if (!invoice.getVerification() && invoice.getBuyerNif().equals(company.value!!.nif)) {
                //nonCheckedInvoices.value!!.add(invoice)
                nonCheckedInvoices.add(invoice)
                //Log.e("X", "nonChecked: "+ invoice.toString())
            } else {
                invoices.value!!.add(invoice)
                //Log.e("Y", "checked: "+ invoice.toString())
            }
        }
    }

    /*fun updateInvoices(){
        for (invoice in nonCheckedInvoices.value!!){
            if (invoice.getVerification() && invoice.getBuyerNif().equals(company.value!!.nif)){
            //if (invoice.verification && invoice.buyerNif.equals(company.value!!.nif)){
                invoices.value!!.add(invoice)
                nonCheckedInvoices.value!!.remove(invoice)
            }
        }
    }*/

    /*fun updateNotifications() : ArrayList<Invoice> {

    }*/
}