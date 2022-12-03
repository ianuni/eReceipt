package com.example.ereceipt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.example.ereceipt.Firebase.FirebaseImplementation
import com.example.ereceipt.Fragments.AddInvoiceFragment
import com.example.ereceipt.Fragments.InboxFragment
import com.example.ereceipt.Fragments.InvoicesFragment
import com.example.ereceipt.Fragments.ProfileFragment


class DockActivity : AppCompatActivity() {
    private lateinit var invoices: ImageButton
    private lateinit var addInvoice: ImageButton
    private lateinit var inbox: ImageButton
    private lateinit var profile: ImageButton
    private lateinit var currentBtn :ImageButton
    private val fireViewModel : FirebaseViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dock)

        fireViewModel.setdata(FirebaseImplementation())

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
}