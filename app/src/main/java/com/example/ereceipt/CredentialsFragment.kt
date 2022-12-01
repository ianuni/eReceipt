package com.example.ereceipt

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.ereceipt.databinding.FragmentSignUpAddressBinding
import com.example.ereceipt.databinding.FragmentSignUpCompanyBinding
import com.example.ereceipt.databinding.FragmentSignUpCredentialsBinding


class CredentialsFragment : Fragment(R.layout.fragment_sign_up_credentials) {
    private lateinit var binding: FragmentSignUpCredentialsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignUpCredentialsBinding.bind(view)

        binding.backButton.setOnClickListener {
            findNavController().navigate(R.id.action_credentialsFragment_to_addressFragment)
        }
    }

}