package com.example.ereceipt

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentResultListener
import androidx.navigation.fragment.findNavController
import com.example.ereceipt.databinding.FragmentLandingBinding
import com.example.ereceipt.databinding.FragmentSignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class SignupFragment : Fragment(R.layout.fragment_signup) {
    private lateinit var binding: FragmentSignupBinding
    private lateinit var firebaseAuth : FirebaseAuth
    private  var companyNif : String? =""
    private var companyName : String? =""
    private var companyPhone : String?=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth= Firebase.auth
        childFragmentManager.setFragmentResultListener("companyKey", this){
            key, bundle ->

        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignupBinding.bind(view)
        binding.toLogIn.setOnClickListener {
            findNavController().navigate(R.id.action_signupFragment_to_loginFragment)
        }
        binding.signUpButton.setOnClickListener {
            Log.e("a", companyNif.toString())
        }

        /*binding.signUpButton.setOnClickListener {
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

        }*/

    }

    private fun checkPasswords(password1 : String, password2 : String){
        if(password1 ==password2) {
            throw Exception("Passwords do not match")
        }
    }

    private fun signUp(email: String, password: String){
        activity?.let {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(it) { task ->
                    if (task.isSuccessful){
                        Log.e("a", "cuenta creada")
                    }
                    else{
                        Log.e("a", task.exception.toString())
                    }
                }
        }
    }

}