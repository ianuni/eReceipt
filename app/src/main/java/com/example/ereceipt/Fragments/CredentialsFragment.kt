package com.example.ereceipt.Fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.example.ereceipt.R
import com.example.ereceipt.databinding.FragmentSignUpCredentialsBinding


class CredentialsFragment : Fragment(R.layout.fragment_sign_up_credentials) {
    private lateinit var binding: FragmentSignUpCredentialsBinding
    private  var bundle: Bundle = bundleOf()
    private var email : String = ""
    private var password : String = ""
    private var passwordConfirmation : String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignUpCredentialsBinding.bind(view)

        if (arguments != null){
            Log.e("a", "arguments")
            email = requireArguments().getString("email").toString()
            password = requireArguments().getString("password").toString()
            passwordConfirmation = requireArguments().getString("passwordConfirmation").toString()
            setState()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bundle.putString("email", binding.emailInput.text.toString())
        bundle.putString("password", binding.passwordInput.text.toString())
        bundle.putString("passwordConfirmation", binding.confirmPasswordInput.text.toString())
        setFragmentResult("credentialsKey", bundle)
    }

    private fun setState(){
        binding.emailInput.setText(email)
        binding.passwordInput.setText(password)
        binding.confirmPasswordInput.setText(passwordConfirmation)
    }


}