package com.example.ereceipt.Fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ereceipt.Model.Invoice
import com.example.ereceipt.Model.Product
import com.example.ereceipt.R
import com.example.ereceipt.ViewModels.CompanyViewModel
import com.example.ereceipt.ViewModels.FirebaseViewModel
import com.example.ereceipt.adapter.ProductAdapter
import com.example.ereceipt.databinding.FragmentAddInvoiceBinding
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList


class AddInvoiceFragment : Fragment(R.layout.fragment_add_invoice) {
    private lateinit var binding :  FragmentAddInvoiceBinding
    private var products = ArrayList<Product>()
    private lateinit var  adapter : ProductAdapter
    private val companyViewModel : CompanyViewModel by activityViewModels()
    private val firebaseViewModel: FirebaseViewModel by activityViewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddInvoiceBinding.bind(view)

        initRecyclerView()

        binding.addButton.setOnClickListener {
            val builder = AlertDialog.Builder(activity)
            val creationView = layoutInflater.inflate(R.layout.create_item, null)
            builder.setView(creationView)
            val dialog = builder.create()
            dialog.show()
            creationView.findViewById<Button>(R.id.add_item).setOnClickListener {
                val name = creationView.findViewById<EditText>(R.id.product_name).text.toString()
                val price = creationView.findViewById<EditText>(R.id.product_price).text.toString().toDouble()
                val amount = creationView.findViewById<EditText>(R.id.product_price).text.toString().toInt()
                val product = Product(name, price, amount)
                products.add(product)

                adapter.notifyItemInserted(products.size)
                dialog.dismiss()
            }
        }

        binding.createBtn.setOnClickListener {
            val invoice = Invoice(binding.buyerNif.toString(), companyViewModel.company.value!!.nif, products, binding.buyerNif.toString().toDouble())
            //val invoice = Invoice("12",true, Date(), products, companyViewModel.company.value!!.nif, true, 7.0, calculateTotal(products),false, Date())
            lifecycleScope.launch{
                firebaseViewModel.myFirebase.value?.createInvoice(invoice)
            }
        }

    }

    private fun initRecyclerView(){
        adapter = ProductAdapter(
            products,
            onClickDelete = {position -> deleteItem(position)}
        )
        binding.productsList.adapter = adapter
        binding.productsList.layoutManager = LinearLayoutManager(activity)

    }

    private fun deleteItem(pos : Int){
        products.removeAt(pos)
        adapter.notifyItemRemoved(pos)
    }

    /*private fun calculateTotal(productList: List<Product>): Double{
        var total: Double = 0.0
        for (product in productList) {
            total += product.amount * product.price
        }
        return total
    }*/
}