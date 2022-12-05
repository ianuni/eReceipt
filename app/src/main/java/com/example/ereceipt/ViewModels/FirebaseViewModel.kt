package com.example.ereceipt.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ereceipt.Firebase.FirebaseImplementation

class FirebaseViewModel : ViewModel() {
    val myFirebase = MutableLiveData<FirebaseImplementation>()

    fun setdata(firebase : FirebaseImplementation){
        myFirebase.value = firebase
    }
}