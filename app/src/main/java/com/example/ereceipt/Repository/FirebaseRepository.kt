package com.example.ereceipt.Repository

import com.example.ereceipt.Model.Company
import com.google.firebase.auth.FirebaseUser

interface FirebaseRepository {

    suspend fun signIn(email: String, password: String): Boolean

    suspend fun signUp(email: String, password: String): FirebaseUser?

    fun logOut() : Boolean

    suspend fun createCompany(user: FirebaseUser, company: Company) : Boolean

    suspend fun getCompany(): Company?

}

