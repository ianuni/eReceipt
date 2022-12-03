package com.example.ereceipt.Fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.ereceipt.DockActivity
import com.example.ereceipt.FirebaseViewModel
import com.example.ereceipt.Model.Company
import com.example.ereceipt.R
import com.example.ereceipt.databinding.FragmentSignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch


class SignupFragment : Fragment(R.layout.fragment_signup) {
    private lateinit var binding: FragmentSignupBinding
    private val viewModel: FirebaseViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignupBinding.bind(view)
        binding.toLogIn.setOnClickListener {
            findNavController().navigate(R.id.action_signupFragment_to_loginFragment)
        }

        binding.signUpButton.setOnClickListener {
            val password1 = binding.passwordInput.text.toString()
            val password2 = binding.confirmPasswordInput.text.toString()
            val email = binding.emailInput.text.toString()
            try{
                checkPasswords(password1, password2)
                signUp(email, password1)
            }
            catch (e: Exception){
                Log.e("a", "aa")
            }

        }

    }

    private fun checkPasswords(password1 : String, password2 : String){
        if(password1 != password2) {
            throw Exception("Passwords do not match")
        }
    }

    private fun signUp(email: String, password: String){
        lifecycleScope.launch{
            val user: FirebaseUser? = viewModel.myFirebase.value?.signUp(email, password)
            if (user != null && (viewModel.myFirebase.value?.createCompany(user, Company("", "", "", "", email, "", "", "")) == true)){
                Log.e("a", "user created")
            } else {
                Log.e("a", "user already exists")
            }
        }
    }
}