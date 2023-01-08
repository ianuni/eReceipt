package com.example.ereceipt.Fragments

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.ereceipt.ViewModels.DatabasesViewModel
import com.example.ereceipt.MainActivity
import com.example.ereceipt.Model.Company
import com.example.ereceipt.R
import com.example.ereceipt.ViewModels.CompanyViewModel
import com.example.ereceipt.databinding.FragmentProfileBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch


class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private lateinit var binding: FragmentProfileBinding
    private val viewModel: DatabasesViewModel by activityViewModels()
    private val companyViewModel : CompanyViewModel by activityViewModels()
    private var nextName = ""
    private var nextPhone = ""
    private var nextAddress = ""
    private var nextPostalCode = ""
    private var nextCity = ""
    private var nextCountry = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)
        setData()

        binding.logOut.setOnClickListener {
            signOut()
        }

        binding.editBtn.setOnClickListener {
            val builder = AlertDialog.Builder(activity)
            val creationView = layoutInflater.inflate(R.layout.dialog_edit_user, null)
            builder.setView(creationView)
            val dialog = builder.create()
            setDialogData(creationView)
            dialog.show()
            creationView.findViewById<Button>(R.id.acceptBtn).setOnClickListener {
                if(
                    checkAddress(creationView) != null ||
                    checkCity(creationView) != null ||
                    checkCountry(creationView) != null ||
                    checkPostalCode(creationView) != null ||
                    checkName(creationView) != null ||
                    checkPhone(creationView) != null){
                    setAddressError(creationView)
                    setCityError(creationView)
                    setCountryError(creationView)
                    setPostalCodeError(creationView)
                    setNameError(creationView)
                    setPhoneError(creationView)
                }
                else{
                    getData(creationView)
                    updateCompany(creationView)
                    dialog.dismiss()
                    setData()
                }

            }
        }
    }

    private fun signOut(){
        if (viewModel.myFirebase.value?.logOut() == true) {
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
        } else{
            Log.e("a", "user is not logged")
        }

    }

    private fun getData(view: View){
        nextName = view.findViewById<TextInputEditText>(R.id.name_input).text.toString()
        nextPhone = view.findViewById<TextInputEditText>(R.id.phone_input).text.toString()
        nextAddress = view.findViewById<TextInputEditText>(R.id.address_input).text.toString()
        nextPostalCode = view.findViewById<TextInputEditText>(R.id.postal_code_input).text.toString()
        nextCity = view.findViewById<TextInputEditText>(R.id.city_input).text.toString()
        nextCountry = view.findViewById<TextInputEditText>(R.id.country_input).text.toString()
    }

    private fun setData(){
        binding.companyName.text = companyViewModel.company.value?.name
        binding.companyNifText.text = companyViewModel.company.value?.nif
        binding.companyPhoneText.text = companyViewModel.company.value?.phoneNumber
        binding.companyEmailText.text = companyViewModel.company.value?.email
        binding.companyAddressText.text = companyViewModel.company.value?.address
        binding.companyPostalCodeText.text = companyViewModel.company.value?.postalCode
        binding.companyCityText.text = companyViewModel.company.value?.city
        binding.companyCountryText.text = companyViewModel.company.value?.country
    }

    private fun setDialogData(view : View){
        view.findViewById<TextInputEditText>(R.id.phone_input).setText(companyViewModel.company.value?.phoneNumber)
        view.findViewById<TextInputEditText>(R.id.name_input).setText(companyViewModel.company.value?.name)
        view.findViewById<TextInputEditText>(R.id.address_input).setText(companyViewModel.company.value?.address)
        view.findViewById<TextInputEditText>(R.id.postal_code_input).setText(companyViewModel.company.value?.postalCode)
        view.findViewById<TextInputEditText>(R.id.city_input).setText(companyViewModel.company.value?.city)
        view.findViewById<TextInputEditText>(R.id.country_input).setText(companyViewModel.company.value?.country)
    }

    private fun updateCompany(view : View){
        val changes = mapOf(
            "name" to nextName,
            "phoneNumber" to nextPhone,
            "address" to nextAddress,
            "postalCode" to nextPostalCode,
            "city" to nextCity,
            "country" to nextCountry)

        val newCompany = Company(
            companyViewModel.company.value?.id!!,
            companyViewModel.company.value?.nif!!,
            nextName,
            nextPhone,
            nextAddress,
            nextPostalCode,
            nextCity,
            nextCountry,
            companyViewModel.company.value?.email!!
        )
        lifecycleScope.launch{viewModel.myFirebase.value?.updateCompany(newCompany.id, changes)}
        companyViewModel.setCompany(newCompany)
    }

    private fun setPhoneError(view : View){
        view.findViewById<TextInputLayout>(R.id.phone_container).helperText = checkPhone(view)
    }

    private fun checkPhone(view : View) : String? {
        val phone = view.findViewById<TextInputEditText>(R.id.phone_input).text.toString()
        if (phone.length != 9){
            return "must be 9 digits"
        }
        if(!phone.matches(".*[0-9].*".toRegex())){
            return "Only digits are allowed"
        }
        return null
    }

    private fun setPostalCodeError(view : View){
        view.findViewById<TextInputLayout>(R.id.postal_code_container).helperText = checkPostalCode(view)
    }

    private fun checkPostalCode(view : View) : String?{
        val postalCode = view.findViewById<TextInputEditText>(R.id.postal_code_input).text.toString()
        if(postalCode.length != 5){
            return "Invalid postal code"
        }
        return null
    }


    private fun setNameError(view : View){
        view.findViewById<TextInputLayout>(R.id.name_container).helperText = checkName(view)
    }
    private fun checkName(view : View) : String?{
        val name = view.findViewById<TextInputEditText>(R.id.name_input).text.toString()
        if(name.isEmpty()){
            return "city is required"
        }
        return null
    }

    private fun setCityError(view : View){
        view.findViewById<TextInputLayout>(R.id.city_container).helperText = checkCity(view)
    }
    private fun checkCity(view : View) : String?{
        val city = view.findViewById<TextInputEditText>(R.id.city_input).text.toString()
        if(city.isEmpty()){
            return "City is required"
        }
        return null
    }

    private fun setCountryError(view : View){
        view.findViewById<TextInputLayout>(R.id.country_container).helperText = checkCountry(view)
    }
    private fun checkCountry(view : View) : String?{
        val country = view.findViewById<TextInputEditText>(R.id.country_input).text.toString()
        if(country.isEmpty()){
            return "Country is required"
        }
        return null
    }

    private fun setAddressError(view : View){
        view.findViewById<TextInputLayout>(R.id.address_container).helperText = checkAddress(view)
    }
    private fun checkAddress(view : View) : String?{
        val address = view.findViewById<TextInputEditText>(R.id.address_input).text.toString()
        if(address.isEmpty()){
            return "Address is required"
        }
        return null
    }
}