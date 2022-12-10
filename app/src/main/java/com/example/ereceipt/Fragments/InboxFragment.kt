package com.example.ereceipt.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ereceipt.Model.Invoice
import com.example.ereceipt.Model.Notification
import com.example.ereceipt.Model.Product
import com.example.ereceipt.R
import com.example.ereceipt.ViewModels.CompanyViewModel
import com.example.ereceipt.ViewModels.DatabasesViewModel
import com.example.ereceipt.adapter.NotificationAdapter
import com.example.ereceipt.adapter.ProductAdapter
import com.example.ereceipt.databinding.FragmentInboxBinding
import kotlinx.coroutines.launch


class InboxFragment : Fragment(R.layout.fragment_inbox) {
    private lateinit var binding : FragmentInboxBinding
    private var notifications = ArrayList<Invoice>()
    private lateinit var  adapter : NotificationAdapter
    private val companyViewModel : CompanyViewModel by activityViewModels()
    private val dbViewModel : DatabasesViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentInboxBinding.bind(view)
        updateNotifications()
    }


    private fun initRecyclerView(){
        adapter = NotificationAdapter(
            notifications,
            onClickDecline = {position -> declineItem(position)},
            onClickAccept = {position -> acceptItem(position)}
        )
        binding.notificationList.adapter = adapter
        binding.notificationList.layoutManager = LinearLayoutManager(activity)
    }

    private fun declineItem(pos: Int){
        //Decline ticket
        lifecycleScope.launch{
            dbViewModel.myFirebase.value?.declineNotification(notifications[pos].getInvoiceId())
        }
        notifications.removeAt(pos)
        adapter.notifyItemRemoved(pos)
    }

    private fun acceptItem(pos: Int){
        //Accept ticket
        lifecycleScope.launch{
            dbViewModel.myFirebase.value?.acceptNotification(notifications[pos].getInvoiceId())
        }
        notifications.removeAt(pos)
        adapter.notifyItemRemoved(pos)
    }

    private fun updateNotifications(){
        lifecycleScope.launch{
            notifications = dbViewModel.myFirebase.value?.updateNotifications(companyViewModel.company.value!!.nif)!!
            if (notifications.size > 0){
                initRecyclerView()
            }
        }
    }
}