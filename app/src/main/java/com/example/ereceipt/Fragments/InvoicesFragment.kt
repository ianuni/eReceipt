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
import com.example.ereceipt.Model.Invoice
import com.example.ereceipt.adapter.ViewInvoiceAdapter
import com.example.ereceipt.R
import com.example.ereceipt.ViewModels.CompanyViewModel
import com.example.ereceipt.ViewModels.DatabasesViewModel
import com.example.ereceipt.databinding.FragmentInvoicesBinding
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
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
        binding.EdTxtSearch.addTextChangedListener { userFilter ->
            val invoiceFiltered = invoices.filter { invoice -> invoice.getBuyerNif().lowercase().contains(userFilter.toString().lowercase()) }
            adapter.updateInvoices(invoiceFiltered)
        }
    }

    private fun initRecyclerView() {
        adapter = ViewInvoiceAdapter(invoices) {
                invoice -> ifSelected(invoice)
        }
        binding.invoicesRecycler.adapter = adapter
        binding.invoicesRecycler.layoutManager = GridLayoutManager(activity, 2)
    }

    private fun updateInvoices(){
        lifecycleScope.launch{
            invoices = dbViewModel.myFirebase.value?.updateInvoices(companyViewModel.company.value!!.nif)!!
            if (invoices.size > 0){
                initRecyclerView()
            }
        }
    }

    fun ifSelected(invoice: Invoice) {
        val builder = AlertDialog.Builder(activity)
        val creationView = layoutInflater.inflate(R.layout.dialog_view_invoice, null)
        builder.setView(creationView)
        val dialog = builder.create()
        setDialogData(creationView, invoice)
        dialog.show()
        creationView.findViewById<Button>(R.id.btnClose).setOnClickListener {
            dialog.dismiss()

        }
        creationView.findViewById<Button>(R.id.btnQR).setOnClickListener {
            try {
                var barcodeEncoder = BarcodeEncoder()
                var bitmap: Bitmap = barcodeEncoder.encodeBitmap(
                    generateQR(invoice),
                    BarcodeFormat.QR_CODE,
                    200,
                    200
                )
                creationView.findViewById<ImageView>(R.id.imgVwQR).setImageBitmap(bitmap)
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
        /*Toast.makeText(
            activity,
            invoice.getInvoiceId(),
            Toast.LENGTH_SHORT
        ).show()*/
    }

    private fun setDialogData(view: View, invoice: Invoice) {
        view.findViewById<TextView>(R.id.txtVwDate).setText(invoice.getCreationDate().toString())
        view.findViewById<TextView>(R.id.txtVwSellerNIF).setText(invoice.getSellerNif())
        view.findViewById<TextView>(R.id.txtVwBuyerNIF).setText(invoice.getBuyerNif())
        view.findViewById<TextView>(R.id.txtVwProducts).setText(invoice.getProductList().toString())
        view.findViewById<TextView>(R.id.txtVwTax).setText(invoice.getTaxesPercent().toString())
        view.findViewById<TextView>(R.id.txtVwTotal).setText(invoice.getTotal().toString())
    }

    private fun generateQR(invoice: Invoice) : String {
        return "Invoice id = " + invoice.getInvoiceId() +
                "\nCreated Date = " + invoice.getCreationDate().toString() +
                "\nSeller NIF = " + invoice.getSellerNif() +
                "\nBuyer NIF = " + invoice.getBuyerNif() +
                "\nProduct List = " + invoice.getProductList().toString() +
                "\nTaxes Percentage = " + invoice.getTaxesPercent().toString() +
                "\nTotal = " + invoice.getTotal().toString()

    }


}

