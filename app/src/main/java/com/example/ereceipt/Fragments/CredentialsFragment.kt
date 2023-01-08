package com.example.ereceipt.Fragments

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.ereceipt.Model.Company
import com.example.ereceipt.R
import com.example.ereceipt.ViewModels.DatabasesViewModel
import com.example.ereceipt.ViewModels.SignUpCompanyViewModel
import com.example.ereceipt.databinding.FragmentSignUpCredentialsBinding
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch


class CredentialsFragment : Fragment(R.layout.fragment_sign_up_credentials) {
    private lateinit var binding: FragmentSignUpCredentialsBinding
    private val signUpCompanyViewModel : SignUpCompanyViewModel by activityViewModels()
    private val viewModel: DatabasesViewModel by activityViewModels()
    private lateinit var company : Company
    private lateinit var password: String
    private lateinit var confirmationPassword: String
    private var emailValid = false
    private var passwordValid = false
    private var confirmationValid = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        company = signUpCompanyViewModel.getCompany()
        password =signUpCompanyViewModel.getPassword()
        confirmationPassword = signUpCompanyViewModel.getConfirmationPassword()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignUpCredentialsBinding.bind(view)
        setState()

        binding.nextBtn.setOnClickListener {
            if(checkPassword() == null && checkEmail() == null && checkConfirmation() == null){
                updateState()
                signUp()
            }
            else{
                invalidForm()
            }
        }

        binding.backBtn.setOnClickListener {
            updateState()
            parentFragmentManager.beginTransaction().replace(R.id.sign_up_container, AddressFragment()).commit()
        }

        binding.emailInput.setOnFocusChangeListener { _, focused ->
            if(!focused){
                setEmailError()
            }
        }
        binding.passwordInput.setOnFocusChangeListener { _, focused ->
            if(!focused){
                setPasswordError()
            }
        }
        binding.confirmPasswordInput.setOnFocusChangeListener { _, focused ->
            if(!focused){
                setConfirmationError()
            }
        }
    }

    private fun setState(){
        binding.emailInput.setText(company.email)
        binding.passwordInput.setText(password)
        binding.confirmPasswordInput.setText(confirmationPassword)
        checkEmail()
        checkPassword()
        checkConfirmation()
    }

    private fun updateState(){
        signUpCompanyViewModel.setEmail(binding.emailInput.text.toString())
        signUpCompanyViewModel.setPassword(binding.passwordInput.text.toString())
        signUpCompanyViewModel.setConfirmationPassword(binding.confirmPasswordInput.text.toString())
    }

    private fun signUp() {
        company = signUpCompanyViewModel.getCompany()
        password =signUpCompanyViewModel.getPassword()
        confirmationPassword = signUpCompanyViewModel.getConfirmationPassword()

        lifecycleScope.launch {
            val user: FirebaseUser? = viewModel.myFirebase.value?.signUp(company.email, password)
            if (user != null && (viewModel.myFirebase.value?.createCompany(user, company) == true)
            ) {
                findNavController().navigate(R.id.action_signupFragment_to_loginFragment)
                Log.e("a", "user created")
            } else Log.e("a", "user already exists")
        }
    }

    private fun setEmailError(){
        binding.emailContainer.helperText = checkEmail()
    }
    private fun checkEmail () : String? {
        val email = binding.emailInput.text.toString()
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailValid = false
            return "Invalid email address"
        }
        emailValid = true
        return null
    }

    private fun setPasswordError(){
        binding.passwordContainer.helperText = checkPassword()
    }
    private fun checkPassword() : String? {
        val password = binding.passwordInput.text.toString()
        if (password.length < 6){
            passwordValid = false
            return "Minimum 6 character password"
        }
        passwordValid = true
        return null
    }

    private fun setConfirmationError(){
        binding.confirmPasswordContainer.helperText = checkConfirmation()
    }
    private fun checkConfirmation() : String? {
        val password = binding.passwordInput.text.toString()
        val confirmation = binding.confirmPasswordInput.text.toString()
        if (password != confirmation){
            confirmationValid = false
            return "Password and confirmation password do not match"
        }
        confirmationValid = true
        return null
    }

    private fun invalidForm() {
        if(!emailValid){
            setEmailError()
        }
        if(!passwordValid){
            setPasswordError()
        }
        if(!confirmationValid){
            setConfirmationError()
        }
    }
}
