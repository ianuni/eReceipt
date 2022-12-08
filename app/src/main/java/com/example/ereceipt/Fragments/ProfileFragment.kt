package com.example.ereceipt.Fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import com.example.ereceipt.ViewModels.DatabasesViewModel
import com.example.ereceipt.MainActivity
import com.example.ereceipt.R
import com.example.ereceipt.databinding.FragmentProfileBinding


class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private lateinit var binding: FragmentProfileBinding
    private val viewModel: DatabasesViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)

        binding.logOut.setOnClickListener {
            signOut()
        }
    }

    private fun signOut(){
        if (viewModel.myFirebase.value?.logOut() == true) {
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
        } else{
            Log.e("a", "user is not logged")
        }

    }
}