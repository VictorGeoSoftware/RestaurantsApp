package com.quandoo.androidtask.ui.customers

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.quandoo.androidtask.utils.Logger
import com.quandoo.androidtask.R
import com.quandoo.androidtask.data.models.Customer
import com.squareup.picasso.Picasso

internal class CustomersRvAdapter(private val tables: List<Customer>,
                                  private val customerClickLstnr: CustomerClickListener) :
        RecyclerView.Adapter<CustomersRvAdapter.CustomerViewHolder>(), Logger {

    interface CustomerClickListener {
        fun onCustomerClick(customer: Customer)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): CustomerViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.customer_cell, viewGroup, false)
        return CustomerViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: CustomerViewHolder, i: Int) {
        val customer = this.tables[i]

        viewHolder.customerName.text = "" + customer.firstName + " " + customer.lastName
        Picasso.get().load(customer.imageUrl).into(viewHolder.customerAvatarImage)
        viewHolder.itemView.setOnClickListener { v ->
            customerClickLstnr.onCustomerClick(customer)
        }
    }

    override fun getItemCount(): Int {
        return tables.size
    }

    internal class CustomerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val customerName: TextView = itemView.findViewById(R.id.customerNameTextView)
        val customerAvatarImage: ImageView = itemView.findViewById(R.id.customerAvatarImageView)
    }
}