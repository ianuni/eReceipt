package com.example.ereceipt.Fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.ereceipt.DockActivity
import com.example.ereceipt.FirebaseViewModel
import com.example.ereceipt.R
import com.example.ereceipt.databinding.FragmentLoginBinding
import kotlinx.coroutines.launch


class LoginFragment : Fragment(R.layout.fragment_login) {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel: FirebaseViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)
        binding.toSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signupFragment)
        }

        binding.loginButton.setOnClickListener {
            Log.e("ATG", "Llegó aqui")
            signIn(binding.emailInput.text.toString(), binding.passwordInput.text.toString())
        }
    }

    private fun signIn(email: String, password: String) {
        lifecycleScope.launch{
            if (viewModel.myFirebase.value?.signIn(email, password) == true){
                val intent = Intent(activity, DockActivity::class.java)
                startActivity(intent)
            } else {
                Log.e("a", "invalid email or password")
            }
        }
    }
    /*private fun signIn(email: String, password: String){
        activity?.let {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(it) { task ->
                    if (task.isSuccessful){
                        Log.e("a","success")
                        val intent = Intent(activity, DockActivity::class.java)
                        startActivity(intent)
                    }
                    else {
                        Log.e("a", "invalid email or password")
                    }
                }
        }
    }*/
}