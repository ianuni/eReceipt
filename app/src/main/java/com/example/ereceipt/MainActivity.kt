package com.example.ereceipt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.ereceipt.Databases.FirebaseImplementation
import com.example.ereceipt.ViewModels.DatabasesViewModel
import com.example.ereceipt.ViewModels.SignUpCompanyViewModel


class MainActivity : AppCompatActivity() {
    private val fireViewModel : DatabasesViewModel by viewModels()
    private val signUpCompany : SignUpCompanyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_EReceipt)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fireViewModel.setFirebase(FirebaseImplementation())

    }

}