package com.example.ereceipt.Repository

import com.example.ereceipt.Model.Company
import com.example.ereceipt.Model.Invoice
import com.google.firebase.auth.FirebaseUser

interface FirebaseRepository {

    suspend fun signIn(email: String, password: String): Boolean

    suspend fun signUp(email: String, password: String): FirebaseUser?

    fun logOut() : Boolean

    suspend fun createCompany(user: FirebaseUser, company: Company) : Boolean

    suspend fun getCompany(): Company?

    suspend fun getCompany(nif: String): Company?

    suspend fun getInvoices(nif: String): Collection<Invoice>

    suspend fun createInvoice(invoice: Invoice): Boolean

    suspend fun updateNotifications(nif : String) : ArrayList<Invoice>

    suspend fun updateInvoices(nif : String) : ArrayList<Invoice>

    suspend fun acceptNotification(invoiceId : String)

    suspend fun declineNotification(invoiceId : String)

    suspend fun updateCompany(companyId : String, changes : Map<String, String>)

    suspend fun getCompanies(userId: String, invoices : List<Invoice>) : Map<String, Company>

    suspend fun deleteInvoice(userId: String, invoice : Invoice)
}

