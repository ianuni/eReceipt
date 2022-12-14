package com.example.ereceipt.Databases

import android.util.Log
import com.example.ereceipt.Model.Company
import com.example.ereceipt.Model.Invoice
import com.example.ereceipt.Repository.FirebaseRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.tasks.await

class FirebaseImplementation constructor(
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val firebaseFireStore: FirebaseFirestore = FirebaseFirestore.getInstance(),
    private val invoiceDB: CollectionReference = firebaseFireStore.collection("invoices"),
    private val companyDB: CollectionReference = firebaseFireStore.collection("companies")

): FirebaseRepository {

    override suspend fun signIn(email: String, password: String) : Boolean {
        return try {
            var res = false
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    res = task.isSuccessful
                    if (res){
                        Log.e("b","success")
                    }
                    else {
                        Log.e("b", "invalid email or password")
                    }
                }
                .await()
            res
        } catch (e: Exception){
            false
        }
    }

    override suspend fun signUp(email: String, password: String) : FirebaseUser? {
        var user: FirebaseUser? = null
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    user = firebaseAuth.currentUser
                    Log.e("b","success")
                }
                else {
                    Log.e("b", "user already exists")
                }
            }
            .await()
        return user
    }

    override fun logOut() : Boolean{
        if (firebaseAuth.currentUser?.uid != null){
            firebaseAuth.signOut()
            return true
        }
        return false
    }


    override suspend fun createCompany(user: FirebaseUser, company: Company) : Boolean{
        return try{
            var res = false
            companyDB.document(user.uid).set(company)
                .addOnCompleteListener{ task ->
                    res = task.isSuccessful
                    if (res){
                        Log.e("c","success on creating company in firestore")
                    } else{
                        Log.e("b","failed on creating company in firestore")
                    }
                }
                .await()
            res
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun getCompany(): Company? {
        return try {
            var company: Company? = null
            companyDB.document(firebaseAuth.currentUser!!.uid)
                .get().addOnSuccessListener { document ->
                    company = document.toObject<Company>()
                    company?.id = document.id
                }
                .await()
            company
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun getCompany(nif: String): Company? {
        return try {
            var company: Company? = null
            companyDB.whereEqualTo("nif", nif)
                .get().addOnSuccessListener { documents ->
                    if (documents.size() == 1){
                        company = documents.first().toObject<Company>()
                    } else if (documents.size() == 0){
                        Log.e("ERR", "No existe compañia con NIF: $nif")
                    }
                    else{
                        Log.e("ERR", "existe más de una compañía con el mismo nif en la base de datos: Nombre= " + documents.first().toObject<Company>().name + ", NIF= " + documents.first().toObject<Company>().nif)
                    }
                }
                .await()
            company
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun getInvoices(nif: String): Collection<Invoice>{
        return try {
            var invoices: MutableCollection<Invoice> = mutableListOf()
            invoiceDB.whereEqualTo("sellerNif", nif)
                .get().addOnSuccessListener { documents ->
                    invoices.addAll(documents.toObjects<Invoice>())
                }
                .await()
            invoiceDB.whereEqualTo("buyerNif", nif)
                .get().addOnSuccessListener { documents ->
                    invoices.addAll(documents.toObjects<Invoice>())
                }
                .await()
            invoices
        } catch (e: Exception) {
            listOf()
        }
    }

    override suspend fun createInvoice(invoice: Invoice): Boolean {
        return try{
            var res = false
            firebaseFireStore.collection("invoices").document().set(invoice)
                .addOnCompleteListener{ task ->
                    res = task.isSuccessful
                    if (res){
                        Log.e("c","success on creating invoice in firestore")
                    } else{
                        Log.e("b","failed on creating invoice in firestore")
                    }
                }
                .await()
            res
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun updateNotifications(nif:String) : ArrayList<Invoice> {
        val notifications = ArrayList<Invoice>()
        invoiceDB.whereEqualTo("buyerNif", nif)
            .whereEqualTo("verification", false)
            .whereEqualTo("buyerView", true).get().addOnSuccessListener { documents ->
                for ((i, document) in documents.withIndex()){
                    notifications.add(document.toObject<Invoice>())
                    notifications[i].setInvoiceId(document.id)
                }
            }
            .await()
        return notifications
    }

    override suspend fun acceptNotification(invoiceId : String){
        invoiceDB.document(invoiceId).update("verification", true)
    }

    override suspend fun declineNotification(invoiceId : String){
        invoiceDB.document(invoiceId).update("buyerView", false)
    }

    override suspend fun updateCompany(companyId: String, changes : Map<String, String>) {
        companyDB.document(companyId).update(changes)
    }

    override suspend fun updateInvoices(nif: String): ArrayList<Invoice> {
        val invoices = ArrayList<Invoice>()
        invoiceDB.whereEqualTo("sellerNif", nif)
            .whereEqualTo("sellerView", true)
            .get().addOnSuccessListener { documents ->
                for ((i, document) in documents.withIndex()){
                    invoices.add(document.toObject<Invoice>())
                    invoices[i].setInvoiceId(document.id)
                }
            }
            .await()
        invoiceDB.whereEqualTo("buyerNif", nif)
            .whereEqualTo("verification", true)
            .whereEqualTo("buyerView", true)
            .get().addOnSuccessListener { documents ->
                for ((i, document) in documents.withIndex()){
                    invoices.add(document.toObject<Invoice>())
                    invoices[i].setInvoiceId(document.id)
                }
            }
            .await()
        return invoices
    }


    override suspend fun getCompanies(userNIF: String, invoices : List<Invoice>): Map<String, Company>{
        val companies = mutableMapOf<String, Company>()
        var company = getCompany(userNIF)
        if (company != null){
            companies[userNIF] = company
        }
        for (invoice in invoices){
            val buyerNif = invoice.getBuyerNif()
            val sellerNif = invoice.getSellerNif()
            if (buyerNif == userNIF && !companies.containsKey(buyerNif)){
                company = getCompany(buyerNif)
                if (company != null){
                    companies[buyerNif] = company
                }
            }
            if (sellerNif == userNIF && !companies.containsKey(sellerNif)){
                company = getCompany(sellerNif)
                if (company != null){
                    companies[sellerNif] = company
                }
            }
        }
        return companies
    }
    fun getFireAuth() {
        Log.e("awd", this.firebaseAuth.app.toString())
    }
}