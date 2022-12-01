package com.example.ereceipt

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.ereceipt.databinding.FragmentSignUpAddressBinding
import com.example.ereceipt.databinding.FragmentSignUpCompanyBinding


class AddressFragment : Fragment(R.layout.fragment_sign_up_address) {
    private lateinit var binding: FragmentSignUpAddressBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignUpAddressBinding.bind(view)

        binding.nextButton.setOnClickListener {
            findNavController().navigate(R.id.action_addressFragment_to_credentialsFragment)
        }
    }

}