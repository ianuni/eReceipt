package com.example.ereceipt.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ereceipt.Model.Notification
import com.example.ereceipt.Model.Product
import com.example.ereceipt.R
import com.example.ereceipt.adapter.NotificationAdapter
import com.example.ereceipt.adapter.ProductAdapter
import com.example.ereceipt.databinding.FragmentInboxBinding


class InboxFragment : Fragment(R.layout.fragment_inbox) {
    private lateinit var binding : FragmentInboxBinding
    private var notifications = ArrayList<Notification>()
    private lateinit var  adapter : NotificationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val notification1 = Notification ("Alfonso", 9.00)
        val notification2 = Notification ("Benearo", 9.67)
        notifications.add(notification1)
        notifications.add(notification2)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentInboxBinding.bind(view)

        initRecyclerView()
    }


    private fun initRecyclerView(){
        adapter = NotificationAdapter(
            notifications
        )
        binding.notificationList.adapter = adapter
        binding.notificationList.layoutManager = LinearLayoutManager(activity)

    }
}