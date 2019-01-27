package com.quandoo.androidtask.ui.customers

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.quandoo.androidtask.utils.Logger
import com.quandoo.androidtask.R
import com.quandoo.androidtask.data.models.Customer
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.customer_cell.view.*

internal class CustomersRvAdapter(private val tables: List<Customer>,
                                  private val customerClickListener: CustomerClickListener) :
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
        viewHolder.bind(customer, customerClickListener)
    }

    override fun getItemCount(): Int {
        return tables.size
    }


    internal class CustomerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(customer: Customer, customerClickListener: CustomerClickListener) = with(itemView) {
            val customerName = customer.firstName + " " + customer.lastName
            customerNameTextView.text = customerName
            Picasso.get().load(customer.imageUrl).into(customerAvatarImageView)

            itemView.setOnClickListener { v ->
                customerClickListener.onCustomerClick(customer)
            }
        }
    }
}