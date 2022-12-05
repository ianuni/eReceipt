package com.example.ereceipt.Model

import java.util.Date


data class Invoice(
    val buyerNif: String = "",
    var buyerView: Boolean = true,
    val creationDate: Date = Date(),
    val productList: Map<String, Float> = mapOf(),
    val sellerNif: String = "",
    var sellerView: Boolean = true,
    val taxesPercent: Float = 0f,
    val total: Float = 0f,
    var verification: Boolean = true,
    val verificationDate: Date = Date()
)
