package com.example.ereceipt.ViewModels

import androidx.lifecycle.ViewModel
import com.example.ereceipt.Model.Company

class SignUpCompanyViewModel : ViewModel() {
    private var company = Company()
    private var password = ""
    private var confirmationPassword = ""

    fun getCompany(): Company{
        return company
    }

    fun getPassword(): String{
        return password
    }
    fun getConfirmationPassword(): String{
        return confirmationPassword
    }

    fun setNif(nif : String){
        company.nif = nif
    }
    fun setName(name : String){
        company.name = name
    }
    fun setPhone(phone : String){
        company.phoneNumber = phone
    }
    fun setAddress(address : String){
        company.address = address
    }
    fun setPostalCode(postalCode : String){
        company.postalCode = postalCode
    }
    fun setCity(city : String){
        company.city = city
    }
    fun setCountry(country : String){
        company.country = country
    }
    fun setEmail(email : String){
        company.email = email
    }
    fun setPassword(new_password :String){
        password = new_password
    }

    fun setConfirmationPassword(new_password :String){
        confirmationPassword = new_password
    }

}