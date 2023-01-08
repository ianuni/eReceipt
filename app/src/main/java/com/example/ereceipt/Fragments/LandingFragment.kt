package com.example.ereceipt.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.ereceipt.R
import com.example.ereceipt.databinding.FragmentLandingBinding


class LandingFragment : Fragment(R.layout.fragment_landing) {
    private lateinit var binding: FragmentLandingBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLandingBinding.bind(view)
        binding.loginButton.setOnClickListener {
            findNavController().navigate(R.id.action_landingFragment_to_loginFragment)
        }

        binding.signUpText.setOnClickListener {
            findNavController().navigate(R.id.action_landingFragment_to_signupFragment)
        }
    }
}