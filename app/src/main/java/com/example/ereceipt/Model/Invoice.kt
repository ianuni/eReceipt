package com.example.ereceipt.Model

import java.util.Date

/*data class Invoice(
    var buyerNif: String = "",
    var buyerView: Boolean = true,
    val creationDate: Date = Date(),
    val productList: List<Product> = listOf(),
    val sellerNif: String = "",
    var sellerView: Boolean = true,
    var taxesPercent: Double = 0.0,
    var total: Double = 0.0,
    var verification: Boolean = false,
    var verificationDate: Date = Date()
)*/


class Invoice {

    private var buyerNif: String = ""
    private var buyerView: Boolean = true
    private var creationDate: Date = Date()
    private var productList: List<Product> = listOf()
    private var sellerNif: String = ""
    private var sellerView: Boolean = true
    private var taxesPercent: Double = 0.0
    private var total: Double = 0.0
    private var verification: Boolean = false
    private var verificationDate: Date = Date()

    constructor(){}

    constructor (buyerNif: String, sellerNif: String, productList : List<Product>, taxesPercent: Double){
        this.buyerNif = buyerNif
        this.sellerNif = sellerNif
        this.productList = productList
        this.taxesPercent = taxesPercent
        this.total = calculateTotal(productList)
    }

    fun getTotal(): Double{
        return this.total
    }

    fun getBuyerView(): Boolean{
        return this.buyerView
    }

    fun getBuyerNif(): String{
        return this.buyerNif
    }

    fun getSellerView(): Boolean{
        return this.sellerView
    }

    fun getSellerNif(): String{
        return this.sellerNif
    }

    fun getCreationDate(): Date{
        return this.creationDate
    }

    fun getProductList(): List<Product>{
        return this.productList
    }

    fun getTaxesPercent(): Double{
        return this.taxesPercent
    }

    fun getVerification(): Boolean{
        return this.verification
    }

    fun getVerificationDate(): Date{
        return this.verificationDate
    }

    fun setTotal(total: Double){
        this.total = total
    }

    fun setBuyerView(buyerView: Boolean){
        this.buyerView = buyerView
    }

    fun setBuyerNif(buyerNif: String){
        this.buyerNif = buyerNif
    }

    fun setSellerView(sellerView: Boolean){
        this.sellerView = sellerView
    }

    fun setSellerNif(sellerNif: String){
        this.sellerNif = sellerNif
    }

    fun setCreationDate(creationDate: Date){
        this.creationDate = creationDate
    }

    fun setProductList(productList: List<Product>){
        this.productList = productList
    }

    fun setTaxesPercent(taxesPercent: Double){
        this.taxesPercent = taxesPercent
    }

    fun setVerification(state: Boolean){
        this.verification = state
    }

    fun setVerificationDate(date: Date){
        this.verificationDate = date
    }

    private fun calculateTotal(productList: List<Product>): Double{
        var total: Double = 0.0
        for (product in productList) {
            total += product.amount * product.price
        }
        return total
    }

    private fun getTotalWithTaxes(): Double{
        return this.total + this.total * this.taxesPercent
    }
}