package com.example.ereceipt.Fragments


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ereceipt.Model.Invoice
import com.example.ereceipt.adapter.ViewInvoiceAdapter
import com.example.ereceipt.R
import com.example.ereceipt.ViewModels.CompanyViewModel
import com.example.ereceipt.ViewModels.DatabasesViewModel
import com.example.ereceipt.databinding.FragmentInvoicesBinding
import kotlinx.coroutines.launch


class InvoicesFragment : Fragment(R.layout.fragment_invoices) {
    private lateinit var binding: FragmentInvoicesBinding
    private var invoices = ArrayList<Invoice>()
    private val companyViewModel: CompanyViewModel by activityViewModels()
    private val dbViewModel : DatabasesViewModel by activityViewModels()
    private lateinit var adapter: ViewInvoiceAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentInvoicesBinding.bind(view)
        updateInvoices()
    }

    private fun initRecyclerView() {
        adapter = ViewInvoiceAdapter(invoices)
        binding.invoicesRecycler.adapter = adapter
        binding.invoicesRecycler.layoutManager = LinearLayoutManager(activity)
    }

    private fun updateInvoices(){
        lifecycleScope.launch{
            invoices = dbViewModel.myFirebase.value?.updateInvoices(companyViewModel.company.value!!.nif)!!
            if (invoices.size > 0){
                initRecyclerView()
            }
        }
    }
}
