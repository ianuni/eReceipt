package com.example.ereceipt.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.example.ereceipt.R
import com.example.ereceipt.databinding.FragmentSignUpCompanyBinding


class CompanyFragment : Fragment(R.layout.fragment_sign_up_company) {
    private lateinit var binding: FragmentSignUpCompanyBinding
    private  var bundle: Bundle = bundleOf()
    private lateinit var  nif: String
    private lateinit var  name: String
    private lateinit var  phoneNumber: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignUpCompanyBinding.bind(view)

        if (arguments != null){
            Log.e("a", "arguments")
            nif = requireArguments().getString("nif").toString()
            name = requireArguments().getString("name").toString()
            phoneNumber = requireArguments().getString("phone").toString()
            setState()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bundle.putString("nif", binding.nifInput.text.toString())
        bundle.putString("name", binding.nameInput.text.toString())
        bundle.putString("phone", binding.phoneInput.text.toString())
        setFragmentResult("companyKey", bundle)
    }

    private fun setState(){
        binding.nifInput.setText(nif)
        binding.nameInput.setText(name)
        binding.phoneInput.setText(phoneNumber)
    }
}