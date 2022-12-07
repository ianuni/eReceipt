package com.example.ereceipt.Model

import java.util.Date


data class Invoice(
    val buyerNif: String = "",
    var buyerView: Boolean = true,
    val creationDate: Date = Date(),
    val productList: List<Product> = listOf(),
    val sellerNif: String = "",
    var sellerView: Boolean = true,
    val taxesPercent: Double = 0.0,
    val total: Double = 0.0,
    var verification: Boolean = true,
    val verificationDate: Date = Date()
)
