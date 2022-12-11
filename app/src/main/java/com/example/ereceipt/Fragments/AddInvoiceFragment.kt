package com.example.ereceipt.Fragments

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
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
import com.example.ereceipt.ViewModels.DatabasesViewModel
import com.example.ereceipt.adapter.ProductAdapter
import com.example.ereceipt.databinding.FragmentAddInvoiceBinding
import kotlinx.coroutines.launch
import kotlin.collections.ArrayList


class AddInvoiceFragment : Fragment(R.layout.fragment_add_invoice) {
    private lateinit var binding :  FragmentAddInvoiceBinding
    private var products = ArrayList<Product>()
    private lateinit var  adapter : ProductAdapter
    private val companyViewModel : CompanyViewModel by activityViewModels()
    private val databasesViewModel: DatabasesViewModel by activityViewModels()
    private var nifValid = false
    private var taxesPercentageValid = false
    var nameValid = false
    var amountValid = false
    var priceValid = false


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddInvoiceBinding.bind(view)

        initRecyclerView()

        binding.buyerNif.setOnFocusChangeListener { _, focused ->
            if(!focused){
                setNifError()
            }
        }

        binding.taxesPercentage.setOnFocusChangeListener { _, focused ->
            if(!focused){
                setTaxesPercentageError()
            }
        }

        binding.addButton.setOnClickListener {
            val builder = AlertDialog.Builder(activity)
            var creationView = layoutInflater.inflate(R.layout.create_item, null)
            builder.setView(creationView)
            val dialog = builder.create()
            dialog.show()

            creationView.findViewById<EditText>(R.id.product_name).setOnFocusChangeListener { _, focused ->
                if(!focused){
                    setProductNameError(creationView)
                }
            }

            creationView.findViewById<EditText>(R.id.product_price).setOnFocusChangeListener { _, focused ->
                if(!focused){
                    setProductPriceError(creationView)
                }
            }

            creationView.findViewById<EditText>(R.id.product_amount).setOnFocusChangeListener { _, focused ->
                if(!focused){
                    setProductAmountError(creationView)
                }
            }
            creationView.findViewById<Button>(R.id.add_item).setOnClickListener {

                if (checkName(creationView) == null && checkPrice(creationView) == null && checkAmount(creationView) == null){
                    val name = creationView.findViewById<EditText>(R.id.product_name).text.toString()
                    val price = creationView.findViewById<EditText>(R.id.product_price).text.toString().toDouble()
                    val amount = creationView.findViewById<EditText>(R.id.product_amount).text.toString().toInt()
                    val product = Product(name, price, amount)
                    products.add(product)

                    adapter.notifyItemInserted(products.size)
                    dialog.dismiss()
                } else {

                    if(!nameValid){
                        setProductNameError(creationView)
                    }
                    if (!priceValid){
                        setProductPriceError(creationView)
                    }
                    if (!amountValid){
                        setProductAmountError(creationView)
                    }
                }
            }
        }

        binding.createBtn.setOnClickListener {
            if (checkNif() == null && checkTaxesPercentage() == null){
                var invoice = Invoice(binding.buyerNif.text.toString(), companyViewModel.company.value!!.nif, products, binding.taxesPercentage.text.toString().toDouble())
                lifecycleScope.launch{
                    databasesViewModel.myFirebase.value?.createInvoice(invoice)
                }
            }else{
                invalidForm()
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

    private fun setNifError(){
        binding.buyerNifContainer.helperText = checkNif()
    }
    private fun checkNif() : String? {
        val nif = binding.buyerNif.text.toString()
        if(!nif.matches("[0-9]{8}[A-Za-z]".toRegex())) {
            nifValid = false
            return "Invalid NIF"
        }
        nifValid = true
        return null
    }

    private fun setTaxesPercentageError(){
        binding.taxesPercentageContainer.helperText = checkTaxesPercentage()
    }
    private fun checkTaxesPercentage() : String? {
        val taxes = binding.taxesPercentage.text.toString()
        if (!taxes.matches("[0-9]{1,2}(\\.[0-9]{1,3})?".toRegex())) {
            taxesPercentageValid = false
            return "The percentage must be a number between 0-100"
        } else if (taxes.toDouble() <= 0 && taxes.toDouble() > 100){
            taxesPercentageValid = false
            return "The percentage must be a number between 0-100"
        }
        taxesPercentageValid = true
        return null
    }

    private fun setProductNameError(creationView: View){
        creationView.findViewById<com.google.android.material.textfield.TextInputLayout>(R.id.product_name_container).helperText= checkName(creationView)
    }
    private fun checkName(creationView : View) : String? {
        val name = creationView.findViewById<EditText>(R.id.product_name).text.toString()
        if(!name.matches("[A-Za-z0-9]+".toRegex())) {
            nameValid = false
            return "Invalid name"
        }
        nameValid = true
        return null
    }

    private fun setProductAmountError(creationView : View){
        creationView.findViewById<com.google.android.material.textfield.TextInputLayout>(R.id.product_amount_container).helperText= checkAmount(creationView)
    }
    private fun checkAmount(creationView : View) : String? {
        val amount = creationView.findViewById<EditText>(R.id.product_amount).text.toString()
        if(!amount.matches("[0-9]+".toRegex())) {
            amountValid = false
            return "Amount must be a whole number"
        }
        amountValid = true
        return null
    }

    private fun setProductPriceError(creationView : View){
        creationView.findViewById<com.google.android.material.textfield.TextInputLayout>(R.id.product_price_container).helperText= checkPrice(creationView)
    }
    private fun checkPrice(creationView : View) : String? {
        val price = creationView.findViewById<EditText>(R.id.product_price).text.toString()
        if(!price.matches("[0-9]+(\\.[0-9]{1,2})?".toRegex())) {
            priceValid = false
            return "Price must be a number"
        }
        priceValid = true
        return null
    }

    private fun invalidForm(){
        if(!nifValid){
            setNifError()
        }
        if (!taxesPercentageValid){
            setTaxesPercentageError()
        }
    }
}