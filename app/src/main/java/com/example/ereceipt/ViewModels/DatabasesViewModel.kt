package com.example.ereceipt.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ereceipt.Databases.FirebaseImplementation
import com.example.ereceipt.Databases.SQLite

class DatabasesViewModel : ViewModel() {
    val myFirebase = MutableLiveData<FirebaseImplementation>()
    val mySQLite = MutableLiveData<SQLite>()

    fun setFirebase(firebase : FirebaseImplementation){
        myFirebase.value = firebase
    }

    fun setSQLite(SQLite: SQLite){
        mySQLite.value = SQLite
    }
}