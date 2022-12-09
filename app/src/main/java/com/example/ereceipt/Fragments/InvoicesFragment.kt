package com.example.ereceipt.Fragments

import android.nfc.NfcAdapter
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ereceipt.Model.Invoice
import com.example.ereceipt.adapter.ViewInvoiceAdapter
import com.example.ereceipt.R
import com.example.ereceipt.ViewModels.CompanyViewModel
import com.example.ereceipt.databinding.FragmentInvoicesBinding


class InvoicesFragment : Fragment(R.layout.fragment_invoices) {
    private lateinit var binding: FragmentInvoicesBinding
    //private var invoiceList = ArrayList<Invoice>()
    private val companyViewModel: CompanyViewModel by activityViewModels()
    //private lateinit var adapter: ViewInvoiceAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentInvoicesBinding.bind(view)
        initRecyclerView()
    }

    fun initRecyclerView() {
        /*adapter = companyViewModel.getCheckedInvoiceList()
            ?.let { ViewInvoiceAdapter(invoiceList = it) }!!
         */
        binding.invoicesRecycler.layoutManager = LinearLayoutManager(activity)
        //binding.invoicesRecycler.adapter = ViewInvoiceAdapter(companyViewModel.invoices.value!!)
        //binding.invoicesRecycler.adapter = adapter
        binding.invoicesRecycler.adapter = companyViewModel.getCheckedInvoiceList()
            ?.let { ViewInvoiceAdapter(it)}
        //adapter.notifyDataSetChanged()
    }

}
