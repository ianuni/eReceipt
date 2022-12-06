package com.example.ereceipt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.ereceipt.Firebase.FirebaseImplementation
import com.example.ereceipt.Fragments.AddInvoiceFragment
import com.example.ereceipt.Fragments.InboxFragment
import com.example.ereceipt.Fragments.InvoicesFragment
import com.example.ereceipt.Fragments.ProfileFragment
import com.example.ereceipt.Model.Company
import com.example.ereceipt.Model.Invoice
import com.example.ereceipt.ViewModels.CompanyViewModel
import com.example.ereceipt.ViewModels.FirebaseViewModel
import kotlinx.coroutines.launch
import java.util.*


class DockActivity : AppCompatActivity() {
    private lateinit var invoices: ImageButton
    private lateinit var addInvoice: ImageButton
    private lateinit var inbox: ImageButton
    private lateinit var profile: ImageButton
    private lateinit var currentBtn :ImageButton
    private val fireViewModel : FirebaseViewModel by viewModels()
    private val companyViewModel : CompanyViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dock)

        loadDataOnViewModels()

        invoices = findViewById(R.id.invoices)
        addInvoice = findViewById(R.id.add_invoice)
        inbox = findViewById(R.id.inbox)
        profile = findViewById(R.id.profile)
        currentBtn = invoices
        val addInvoiceFragment = AddInvoiceFragment()
        val invoicesFragment = InvoicesFragment()
        val inboxFragment = InboxFragment()
        val profileFragment = ProfileFragment()

        invoices.setOnClickListener {
            onClickHandle(invoicesFragment, invoices)
        }
        addInvoice.setOnClickListener {
            onClickHandle(addInvoiceFragment, addInvoice)
        }
        inbox.setOnClickListener {
            onClickHandle(inboxFragment, inbox)
        }
        profile.setOnClickListener {
            onClickHandle(profileFragment, profile)
        }

    }

    override fun onBackPressed() {
        return
    }

    private fun colorIcon (btn: ImageButton, color : Int) {
        btn.background.setTint(resources.getColor(color))
    }

    private fun navigate (nextFragment : Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.currentFragment, nextFragment).commit()
    }

    private fun updateDock (nextBtn : ImageButton) {
        colorIcon(currentBtn, R.color.black)
        colorIcon(nextBtn, R.color.blue)
        currentBtn = nextBtn
    }

    private fun onClickHandle(nextFragment : Fragment, nextBtn : ImageButton) {
        if (nextBtn != currentBtn){
            navigate(nextFragment)
            updateDock(nextBtn)
        }
    }

    private fun loadDataOnViewModels(){
        fireViewModel.setdata(FirebaseImplementation())
        lifecycleScope.launch{
            val company: Company? = fireViewModel.myFirebase.value?.getCompany()
            if (company != null) {
                companyViewModel.setCompany(company)
                val invoices: Collection<Invoice>? = fireViewModel.myFirebase.value?.getInvoices(company.nif)
                Log.e("adw", "llego aqui")
                if (invoices != null){
                    Log.e("adw", invoices.toString())
                    companyViewModel.setInvoices(invoices)
                }else{
                    //¿Qué pasaría??????????
                    Log.e("a", "couldnt load invoices")
                }
            } else{
                //fireViewModel.myFirebase.value?.logOut()
                //¿Qué pasaría??????????
                Log.e("a", "couldnt load company")
            }
            //fireViewModel.myFirebase.value?.createInvoice(Invoice("12345678S",true, Date(), mapOf("atun" to 2.33f, "millo" to 1f, "mahonesa" to 23.3f), "4", true, 7f, 50.1f,false, Date()))
            //fireViewModel.myFirebase.value?.createInvoice(Invoice("12345678S",true, Date(), mapOf("papas" to 2.33f), "4", true, 7f, 50.1f,true, Date()))
            //fireViewModel.myFirebase.value?.createInvoice(Invoice("12",true, Date(), mapOf("cangrejo" to 2.33f), "12345678S", true, 7f, 50.1f,false, Date()))
            //fireViewModel.myFirebase.value?.createInvoice(Invoice("12",true, Date(), mapOf("chistorra" to 2.33f), "12345678S", true, 7f, 50.1f,true, Date()))

        }
    }
}