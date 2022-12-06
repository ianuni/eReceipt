package com.example.ereceipt.Model

import java.util.Date


data class Invoice(
    val buyerNif: String = "",
    var buyerView: Boolean = true,
    val creationDate: Date = Date(),
    val productList: Map<String, Float> = mapOf(),
    val sellerNif: String = "",
    var sellerView: Boolean = true,
    val taxesPercent: Double = 0.0,
    val total: Double = 0.0,
    var verification: Boolean = true,
    val verificationDate: Date = Date()
)
