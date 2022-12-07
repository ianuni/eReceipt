package com.example.ereceipt.Fragments

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.View

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.ereceipt.ViewModels.FirebaseViewModel
import com.example.ereceipt.Model.Company
import com.example.ereceipt.R
import com.example.ereceipt.databinding.FragmentSignupBinding
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch


class SignupFragment : Fragment(R.layout.fragment_signup) {
    private lateinit var binding: FragmentSignupBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignupBinding.bind(view)
        childFragmentManager.beginTransaction().replace(R.id.sign_up_container, CompanyFragment()).commit()

        binding.toLogIn.setOnClickListener {
            findNavController().navigate(R.id.action_signupFragment_to_loginFragment)
        }
    }

}
