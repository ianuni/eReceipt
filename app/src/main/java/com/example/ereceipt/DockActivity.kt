package com.example.ereceipt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.ereceipt.Databases.FirebaseImplementation
import com.example.ereceipt.Databases.SQLite
import com.example.ereceipt.Fragments.AddInvoiceFragment
import com.example.ereceipt.Fragments.InboxFragment
import com.example.ereceipt.Fragments.InvoicesFragment
import com.example.ereceipt.Fragments.ProfileFragment
import com.example.ereceipt.Model.Company
import com.example.ereceipt.Model.Invoice
import com.example.ereceipt.ViewModels.CompanyViewModel
import com.example.ereceipt.ViewModels.DatabasesViewModel
import kotlinx.coroutines.launch


class DockActivity : AppCompatActivity() {
    private lateinit var invoices: ImageButton
    private lateinit var addInvoice: ImageButton
    private lateinit var inbox: ImageButton
    private lateinit var profile: ImageButton
    private lateinit var currentBtn :ImageButton
    private val dbViewModel : DatabasesViewModel by viewModels()
    private val companyViewModel : CompanyViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dock)
        dbViewModel.setFirebase(FirebaseImplementation())
        dbViewModel.setSQLite(SQLite(this))
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

        invoices.isClickable = false
        addInvoice.isClickable = false
        inbox.isClickable = false
        profile.isClickable = false
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
        dbViewModel.setFirebase(FirebaseImplementation())
        dbViewModel.setSQLite(SQLite(this))
        var company: Company? = null
        lifecycleScope.launch {
            for (i in 1..10){
                company = dbViewModel.myFirebase.value?.getCompany()
                if (company != null) {
                    companyViewModel.setCompany(company!!)
                    Log.e("a","go to fragment")
                    navigate(InvoicesFragment())
                    invoices.isClickable = true
                    addInvoice.isClickable = true
                    inbox.isClickable = true
                    profile.isClickable = true
                    Log.e("INTENTOS", "el número de intentos para cargar la compañia fueron: " + i.toString())
                    break
                }
            }
            if (company != null) {
                val invoices: Collection<Invoice>? = dbViewModel.myFirebase.value?.getInvoices(company!!.nif)
                if (invoices != null) {
                    companyViewModel.setInvoices(invoices)
                    //updateLocalDatabase()
                } else Log.e("a", "couldnt load invoices")
            } else Log.e("a", "couldnt load company")
            Log.e("a", "cargo los datos")
        }

        //fireViewModel.myFirebase.value?.createInvoice(Invoice("12345678S",true, Date(), mapOf("atun" to 2.33f, "millo" to 1f, "mahonesa" to 23.3f), "4", true, 7f, 50.1f,false, Date()))
        //fireViewModel.myFirebase.value?.createInvoice(Invoice("12345678S",true, Date(), mapOf("papas" to 2.33f), "4", true, 7f, 50.1f,true, Date()))
        //fireViewModel.myFirebase.value?.createInvoice(Invoice("12",true, Date(), mapOf("cangrejo" to 2.33f), "12345678S", true, 7f, 50.1f,false, Date()))
        //fireViewModel.myFirebase.value?.createInvoice(Invoice("12",true, Date(), listOf(Product("chistorra", 4.5, 3)), "12345678S", true, 7.0, 50.1,true, Date()))
    }

    private fun updateLocalDatabase(){
        lifecycleScope.launch {
            /*for (invoice in companyViewModel.nonCheckedInvoices.value!!) { //Si te han emitido cualquier factuta
                var company = dbViewModel.myFirebase.value?.getCompany(invoice.getSellerNif())
                if (company != null) dbViewModel.mySQLite.value?.addCompany(company)
            }*/
            for (invoice in companyViewModel.invoices.value!!) {
                if (invoice.getSellerNif().equals(companyViewModel.company.value?.nif) && invoice.getVerification()) { //Si eres vendedor y está verificada la factura
                    var company = dbViewModel.myFirebase.value?.getCompany(invoice.getBuyerNif())
                    if (company != null) dbViewModel.mySQLite.value?.addCompany(company)
                }
            }
            Log.e("SQLite", "Compañias en la base de datos local: " + dbViewModel.mySQLite.value?.getCompanies().toString())

        }
    }
    private fun pruebaDB(){
        dbViewModel.mySQLite.value?.onUpgrade(dbViewModel.mySQLite.value?.writableDatabase, 1, 1)
        /*dbViewModel.mySQLite.value?.addCompany(Company("NIF", "NAME", "PHONENUMBER", "ADDRESS", "POSTALCODE", "CITY", "COUNTRY", "EMAIL"))
        dbViewModel.mySQLite.value?.addCompany(Company("NIF", "NAME", "PHONENUMBER", "ADDRESS", "POSTALCODE", "CITY", "COUNTRY", "EMAIL"))
        dbViewModel.mySQLite.value?.addCompany(Company("OTRONIF", "NAME", "PHONENUMBER", "ADDRESS", "POSTALCODE", "CITY", "COUNTRY", "EMAIL"))
        Log.e("SQLite", "Se lee la compañia: " + dbViewModel.mySQLite.value?.getCompany("NIF").toString())
        Log.e("SQLite", "Se lee la compañia: " + dbViewModel.mySQLite.value?.getCompanies().toString())*/

    }
}
