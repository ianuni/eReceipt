package com.example.ereceipt.Repository

import com.example.ereceipt.Model.Company

interface FirebaseRepository {

    suspend fun signIn(email: String, password: String): Boolean

    suspend fun signUp(email: String, password: String): Boolean

    fun logOut() : Boolean

    suspend fun createCompany(id: String, company: Company)

    suspend fun getCompany(id: String): Company


}

