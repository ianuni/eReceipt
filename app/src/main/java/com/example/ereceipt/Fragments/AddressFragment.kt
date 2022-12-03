package com.example.ereceipt.Fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.example.ereceipt.R
import com.example.ereceipt.databinding.FragmentSignUpAddressBinding


class AddressFragment : Fragment(R.layout.fragment_sign_up_address) {
    private lateinit var binding: FragmentSignUpAddressBinding
    private  var bundle: Bundle = bundleOf()
    private var address : String = ""
    private var postalCode : String = ""
    private var city : String = ""
    private var country : String=""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignUpAddressBinding.bind(view)

        if (arguments != null){
            Log.e("a", "arguments")
            address = requireArguments().getString("address").toString()
            postalCode = requireArguments().getString("postalCode").toString()
            city = requireArguments().getString("city").toString()
            country = requireArguments().getString("country").toString()
            setState()
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        bundle.putString("address", binding.addressInput.text.toString())
        bundle.putString("postalCode", binding.postalCodeInput.text.toString())
        bundle.putString("city", binding.cityInput.text.toString())
        bundle.putString("country", binding.countryInput.text.toString())
        setFragmentResult("addressKey", bundle)
    }

    private fun setState(){
        binding.addressInput.setText(address)
        binding.postalCodeInput.setText(postalCode)
        binding.cityInput.setText(city)
        binding.countryInput.setText(country)
    }

}