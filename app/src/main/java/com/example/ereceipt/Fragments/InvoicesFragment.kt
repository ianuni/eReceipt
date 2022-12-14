package com.example.ereceipt.Fragments


import android.app.AlertDialog
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ereceipt.Model.Company
import com.example.ereceipt.Model.Invoice
import com.example.ereceipt.Model.Product
import com.example.ereceipt.adapter.ViewInvoiceAdapter
import com.example.ereceipt.R
import com.example.ereceipt.ViewModels.CompanyViewModel
import com.example.ereceipt.ViewModels.DatabasesViewModel
import com.example.ereceipt.adapter.InvoiceProductAdapter
import com.example.ereceipt.databinding.FragmentInvoicesBinding
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import kotlinx.coroutines.launch




class InvoicesFragment : Fragment(R.layout.fragment_invoices) {
    private lateinit var binding: FragmentInvoicesBinding
    private var invoices = ArrayList<Invoice>()
    private var companies = mapOf<String, Company>()
    private val companyViewModel: CompanyViewModel by activityViewModels()
    private val dbViewModel : DatabasesViewModel by activityViewModels()
    private lateinit var adapter: ViewInvoiceAdapter
    private lateinit var productListAdapter : InvoiceProductAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentInvoicesBinding.bind(view)
        updateInvoices()
        binding.EdTxtSearch.addTextChangedListener { userFilter ->
            val invoiceFiltered = invoices.filter { invoice -> invoice.getBuyerNif().lowercase().contains(userFilter.toString().lowercase()) }
            adapter.updateInvoices(invoiceFiltered)
        }
    }

    private fun initRecyclerView() {
        adapter = ViewInvoiceAdapter(invoices, companies) {
                invoice -> ifSelected(invoice)
        }
        binding.invoicesRecycler.adapter = adapter
        binding.invoicesRecycler.layoutManager = GridLayoutManager(activity, 2)
    }

    private fun updateInvoices(){
        lifecycleScope.launch{
            invoices = dbViewModel.myFirebase.value?.updateInvoices(companyViewModel.company.value!!.nif)!!
            if (invoices.size > 0){
                lifecycleScope.launch{
                    companies = dbViewModel.myFirebase.value?.getCompanies(companyViewModel.company.value!!.nif, invoices)!!
                    initRecyclerView()
                }
            }
        }
    }

    private fun ifSelected(invoice: Invoice) {
        val builder = AlertDialog.Builder(activity)
        val creationView = layoutInflater.inflate(R.layout.dialog_view_invoice, null)
        builder.setView(creationView)
        val dialog = builder.create()
        setDialogData(creationView, invoice)
        dialog.show()
        creationView.findViewById<Button>(R.id.btnClose).setOnClickListener {
            dialog.dismiss()
        }
    }

    private fun setDialogData(view: View, invoice: Invoice) {
        val sellerNif = invoice.getSellerNif()
        val buyerNif = invoice.getBuyerNif()
        view.findViewById<TextView>(R.id.date).text = invoice.getCreationDate().toString()
        view.findViewById<TextView>(R.id.seller_nif).text = sellerNif
        view.findViewById<TextView>(R.id.buyer_nif).text = buyerNif
        if (companies.containsKey(sellerNif)){
            view.findViewById<TextView>(R.id.seller_address).text = companies[sellerNif]!!.address
            view.findViewById<TextView>(R.id.seller_postal_code).text = companies[sellerNif]!!.postalCode
            view.findViewById<TextView>(R.id.seller_city).text = companies[sellerNif]!!.city
            view.findViewById<TextView>(R.id.seller_country).text = companies[sellerNif]!!.country
            view.findViewById<TextView>(R.id.seller_email).text = companies[sellerNif]!!.email
        }

        if (companies.containsKey(buyerNif)){
            view.findViewById<TextView>(R.id.buyer_address).text = companies[buyerNif]!!.address
            view.findViewById<TextView>(R.id.buyer_postal_code).text = companies[buyerNif]!!.postalCode
            view.findViewById<TextView>(R.id.buyer_city).text = companies[buyerNif]!!.city
            view.findViewById<TextView>(R.id.buyer_country).text = companies[buyerNif]!!.country
            view.findViewById<TextView>(R.id.buyer_email).text = companies[buyerNif]!!.email
        }

        val taxes = invoice.getTaxesPercent().toString() + "%"
        view.findViewById<TextView>(R.id.taxes_value).text = taxes
        view.findViewById<TextView>(R.id.subtotal_value).text = invoice.getTotal().toString()
        view.findViewById<TextView>(R.id.tax_base_value).text = invoice.getTaxBase().toString()

        var products = arrayListOf<Product>()
        products.addAll(invoice.getProductList())
        productListAdapter = InvoiceProductAdapter(products)
        view.findViewById<RecyclerView>(R.id.product_recycler).adapter = productListAdapter
        view.findViewById<RecyclerView>(R.id.product_recycler).layoutManager = LinearLayoutManager(activity)

    }


}

