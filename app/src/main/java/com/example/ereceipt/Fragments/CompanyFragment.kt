package com.example.ereceipt.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import com.example.ereceipt.Model.Company
import com.example.ereceipt.R
import com.example.ereceipt.ViewModels.SignUpCompanyViewModel
import com.example.ereceipt.databinding.FragmentSignUpCompanyBinding


class CompanyFragment : Fragment(R.layout.fragment_sign_up_company) {
    private lateinit var binding: FragmentSignUpCompanyBinding
    private val signUpCompanyViewModel : SignUpCompanyViewModel by activityViewModels()
    private lateinit var company : Company
    private var phoneValid = false
    private var nifValid = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        company = signUpCompanyViewModel.getCompany()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignUpCompanyBinding.bind(view)
        setState()

        binding.nextBtn.setOnClickListener {
            if(checkNif() == null && checkPhone() == null){
                updateState()
                parentFragmentManager.beginTransaction().replace(R.id.sign_up_container, AddressFragment()).commit()
            }
            else{
                invalidForm()
            }
        }

        binding.nifInput.setOnFocusChangeListener { _, focused ->
            if(!focused){
                setNifError()
            }
        }
        binding.phoneInput.setOnFocusChangeListener { _, focused ->
            if(!focused){
                setPhoneError()
            }
        }
    }

    private fun setState(){
        binding.nifInput.setText(company.nif)
        binding.nameInput.setText(company.name)
        binding.phoneInput.setText(company.phoneNumber)
        checkNif()
        checkPhone()
    }

    private fun updateState(){
        signUpCompanyViewModel.setNif(binding.nifInput.text.toString())
        signUpCompanyViewModel.setName(binding.nameInput.text.toString())
        signUpCompanyViewModel.setPhone(binding.phoneInput.text.toString())
    }


    private fun setNifError(){
        binding.nifContainer.helperText = checkNif()
    }
    private fun checkNif() : String? {
        val nif = binding.nifInput.text.toString()
        if(!nif.matches("[0-9]{8}[A-Za-z]".toRegex())) {
            nifValid = false
            return "Invalid NIF"
        }
        nifValid = true
        return null
    }

    private fun setPhoneError(){
        binding.phoneContainer.helperText = checkPhone()
    }
    private fun checkPhone() : String? {
        val phone = binding.phoneInput.text.toString()
        if (phone.length != 9){
            phoneValid = false
            return "must be 9 digits"
        }
        if(!phone.matches(".*[0-9].*".toRegex())){
            phoneValid = false
            return "Only digits are allowed"
        }
        phoneValid = true
        return null
    }

    private fun invalidForm(){
        if(!nifValid){
            setNifError()
        }
        if(!phoneValid){
            setPhoneError()
        }
    }
}
