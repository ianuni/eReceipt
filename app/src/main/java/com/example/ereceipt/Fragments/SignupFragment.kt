package com.example.ereceipt.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.ereceipt.ViewModels.FirebaseViewModel
import com.example.ereceipt.Model.Company
import com.example.ereceipt.R
import com.example.ereceipt.databinding.FragmentSignupBinding
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch


class SignupFragment : Fragment(R.layout.fragment_signup) {
    private lateinit var binding: FragmentSignupBinding
    private val viewModel: FirebaseViewModel by activityViewModels()
    private var currentView : Int = 1
    var company : Company = Company()
    var password: String = ""
    var passwordConfirmation: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignupBinding.bind(view)
        goToCompany()

        binding.toLogIn.setOnClickListener {
            findNavController().navigate(R.id.action_signupFragment_to_loginFragment)
        }

        binding.nextButton.setOnClickListener {
            goNext()
        }

        binding.backButton.setOnClickListener {
            goBack()
        }

        childFragmentManager.setFragmentResultListener("companyKey", this){
                key, bundle ->
            company.nif = bundle.getString("nif").toString()
            company.name = bundle.getString("name").toString()
            company.phoneNumber = bundle.getString("phone").toString()
        }

        childFragmentManager.setFragmentResultListener("addressKey", this){
                key, bundle ->
            company.address = bundle.getString("address").toString()
            company.postalCode = bundle.getString("postalCode").toString()
            company.city = bundle.getString("city").toString()
            company.country = bundle.getString("country").toString()
        }

        childFragmentManager.setFragmentResultListener("credentialsKey", this){
                key, bundle ->
            Log.e("a", "interrupt")
            company.email = bundle.getString("email").toString()
            password = bundle.getString("password").toString()
            passwordConfirmation = bundle.getString("passwordConfirmation").toString()
        }

        binding.signUpButton.setOnClickListener {
            // TODO: Include signup logic below here
            signUp()
        }



    }

    private fun goNext():Int{
        if (currentView == 4){
            return -1
        }
        if (currentView == 3) {
            goToFinal()
            binding.signUpButton.visibility = View.VISIBLE
        }
        if(currentView == 1){
            goToAddress()
            binding.backButton.visibility = View.VISIBLE
        }
        if(currentView == 2){
            goToCredentials()

        }
        currentView++
        return 0
    }


    private fun signUp() {
        lifecycleScope.launch {
            val user: FirebaseUser? = viewModel.myFirebase.value?.signUp(company.email, password)
            if (user != null && (viewModel.myFirebase.value?.createCompany(user, company) == true)
            ) {
                findNavController().navigate(R.id.action_signupFragment_to_loginFragment)
                Log.e("a", "user created")
            } else Log.e("a", "user already exists")
        }
    }

    private fun goBack():Int{
        if (currentView == 1) {
            return -1
        }
        if(currentView == 2){
            goToCompany()
        }
        if(currentView == 3){
            goToAddress()
            binding.nextButton.visibility = View.VISIBLE
        }
        if (currentView == 4){
            goToCredentials()
            binding.signUpButton.visibility = View.INVISIBLE
            binding.nextButton.visibility = View.VISIBLE
        }
        currentView--
        return 0
    }

    private fun goToCompany(){
        val bundle = Bundle()
        bundle.putString("nif", company.nif)
        bundle.putString("name", company.name)
        bundle.putString("phone", company.phoneNumber)
        val transaction = childFragmentManager.beginTransaction()
        val fragment = CompanyFragment()
        fragment.arguments = bundle
        transaction.replace(R.id.sign_up_container, fragment)
        transaction.commit()
        binding.backButton.visibility = View.INVISIBLE

    }

    private fun goToAddress(){
        val bundle = Bundle()
        bundle.putString("address", company.address)
        bundle.putString("postalCode", company.postalCode)
        bundle.putString("city", company.city)
        bundle.putString("country", company.country)
        val transaction = childFragmentManager.beginTransaction()
        val fragment = AddressFragment()
        fragment.arguments = bundle
        transaction.replace(R.id.sign_up_container, fragment)
        transaction.commit()
    }

    private fun goToCredentials(){
        val bundle = Bundle()
        bundle.putString("email", company.email)
        bundle.putString("password", password)
        bundle.putString("passwordConfirmation", passwordConfirmation)
        val transaction = childFragmentManager.beginTransaction()
        val fragment = CredentialsFragment()
        fragment.arguments = bundle
        transaction.replace(R.id.sign_up_container, fragment)
        transaction.commit()
        //binding.nextButton.visibility = View.INVISIBLE
    }

    private fun goToFinal(){
        val transaction = childFragmentManager.beginTransaction()
        val fragment = SignUpFinalFragment()
        transaction.replace(R.id.sign_up_container, fragment)
        transaction.commit()
        binding.nextButton.visibility = View.INVISIBLE
    }

}