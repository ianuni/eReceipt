package com.example.ereceipt

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.example.ereceipt.databinding.FragmentSignUpCompanyBinding


class CompanyFragment : Fragment(R.layout.fragment_sign_up_company) {
    private lateinit var binding: FragmentSignUpCompanyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignUpCompanyBinding.bind(view)

        binding.nextButton.setOnClickListener {
            setFragmentResult("companyKey", bundleOf("companyNif" to binding.nifInput.text.toString()))
            findNavController().navigate(R.id.action_companyFragment_to_addressFragment)
        }
    }



}