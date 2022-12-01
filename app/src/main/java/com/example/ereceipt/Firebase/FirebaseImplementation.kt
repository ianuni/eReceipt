package com.example.ereceipt.Firebase

import android.util.Log
import com.example.ereceipt.Model.Company
import com.example.ereceipt.Repository.FirebaseRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirebaseImplementation constructor(
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val firebaseFireStore: FirebaseFirestore = FirebaseFirestore.getInstance()

): FirebaseRepository {

    override suspend fun signIn(email: String, password: String) : Boolean {
        return try {
            var res : Boolean = false
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

    override suspend fun signUp(email: String, password: String) : Boolean {
        return try {
            var res : Boolean = false
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    res = task.isSuccessful
                    if (res){
                        Log.e("b","success")
                    }
                    else {
                        Log.e("b", "user already exists")
                    }
                }
                .await()
            res
        } catch (e: Exception){
            false
        }
    }

    override fun logOut() : Boolean{
        if (firebaseAuth.currentUser?.uid != null){
            firebaseAuth.signOut()
            return true
        }
        return false
    }

    override suspend fun createCompany(id: String, company: Company){
        firebaseFireStore.collection("companies").document(id).set(company)
            .addOnSuccessListener { documentReference ->
                //Log.d("b", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
    }

    override suspend fun getCompany(id: String): Company {
        /*try{
            val company: Company = firebaseFireStore.collection("companies").document(firebaseAuth.currentUser.uid).get()
        } catch (Exception e){

        }*/
        return Company("")
    }

    fun getFire() {
        Log.e("awd", this.firebaseAuth.app.toString())
    }
}