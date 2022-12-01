package com.example.ereceipt

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ereceipt.Firebase.FirebaseImplementation

class FirebaseViewModel : ViewModel() {
    val myFirebase = MutableLiveData<FirebaseImplementation>()

    fun setdata(firebase : FirebaseImplementation){
        myFirebase.value = firebase
    }
}