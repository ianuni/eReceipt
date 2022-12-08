package com.example.ereceipt.Fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import com.example.ereceipt.Model.Company
import com.example.ereceipt.R
import com.example.ereceipt.ViewModels.SignUpCompanyViewModel
import com.example.ereceipt.databinding.FragmentSignUpAddressBinding


class AddressFragment : Fragment(R.layout.fragment_sign_up_address) {
    private lateinit var binding: FragmentSignUpAddressBinding
    private val signUpCompanyViewModel : SignUpCompanyViewModel by activityViewModels()
    private lateinit var company : Company
    private var postalCodeValid = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        company = signUpCompanyViewModel.getCompany()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignUpAddressBinding.bind(view)
        setState()

        binding.nextBtn.setOnClickListener {
            if(checkPostalCode() == null){
                updateState()
                parentFragmentManager.beginTransaction().replace(R.id.sign_up_container, CredentialsFragment()).commit()
            }
            else{
                invalidForm()
            }
        }

        binding.backBtn.setOnClickListener {
            updateState()
            parentFragmentManager.beginTransaction().replace(R.id.sign_up_container, CompanyFragment()).commit()
        }

        binding.postalCodeInput.setOnFocusChangeListener { _, focused ->
            if(!focused){
                setPostalCodeError()
            }
        }
    }

    private fun setState(){
        binding.addressInput.setText(company.address)
        binding.postalCodeInput.setText(company.postalCode)
        binding.cityInput.setText(company.city)
        binding.countryInput.setText(company.country)
        checkPostalCode()
    }

    private fun updateState(){
        signUpCompanyViewModel.setAddress(binding.addressInput.text.toString())
        signUpCompanyViewModel.setPostalCode(binding.postalCodeInput.text.toString())
        signUpCompanyViewModel.setCity(binding.cityInput.text.toString())
        signUpCompanyViewModel.setCountry(binding.countryInput.text.toString())
    }

    private fun setPostalCodeError(){
        binding.postalCodeContainer.helperText = checkPostalCode()
    }
    private fun checkPostalCode() : String?{
        val postalCode = binding.postalCodeInput.text.toString()
        if(postalCode.length != 5){
            postalCodeValid = false
            return "Invalid postal code"
        }
        postalCodeValid = true
        return null
    }
    private fun invalidForm(){
        if(!postalCodeValid){
            setPostalCodeError()
        }
    }
}
