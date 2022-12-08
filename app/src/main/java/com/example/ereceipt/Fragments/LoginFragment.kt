package com.example.ereceipt.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.ereceipt.DockActivity
import com.example.ereceipt.ViewModels.DatabasesViewModel
import com.example.ereceipt.R
import com.example.ereceipt.databinding.FragmentLoginBinding
import kotlinx.coroutines.launch


class LoginFragment : Fragment(R.layout.fragment_login) {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel: DatabasesViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)
        binding.toSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signupFragment)
        }

        binding.loginButton.setOnClickListener {
            signIn(binding.emailInput.text.toString(), binding.passwordInput.text.toString())
        }
    }

    private fun signIn(email: String, password: String) {
        lifecycleScope.launch{
            if (viewModel.myFirebase.value?.signIn(email, password) == true){
                val intent = Intent(activity, DockActivity::class.java)
                startActivity(intent)
                /*
                val company: Company? = viewModel.myFirebase.value?.getCompany()
                Log.e("a", company?.city.toString())
                if (company != null) {
                    startActivity(intent)
                } else{
                    Log.e("end", "couldnt load company")
                }*/
            } else {
                Toast.makeText(activity, "Email or password is incorrect", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
