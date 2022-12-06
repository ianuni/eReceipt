package com.example.ereceipt.Fragments

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ereceipt.Model.Product
import com.example.ereceipt.R
import com.example.ereceipt.adapter.ProductAdapter
import com.example.ereceipt.databinding.FragmentAddInvoiceBinding


class AddInvoiceFragment : Fragment(R.layout.fragment_add_invoice) {
    private lateinit var binding :  FragmentAddInvoiceBinding
    private var products = ArrayList<Product>()
    private lateinit var  adapter : ProductAdapter

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
                val amount = 1.0
                val product = Product(name, price, amount)
                products.add(product)
                adapter.notifyItemInserted(products.size)
                dialog.dismiss()
            }
        }

        binding.createBtn.setOnClickListener {

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
}