package com.example.ereceipt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.example.ereceipt.Firebase.FirebaseImplementation
import com.example.ereceipt.ViewModels.FirebaseViewModel


class MainActivity : AppCompatActivity() {
    private val fireViewModel : FirebaseViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_EReceipt)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fireViewModel.setdata(FirebaseImplementation())

    }

}