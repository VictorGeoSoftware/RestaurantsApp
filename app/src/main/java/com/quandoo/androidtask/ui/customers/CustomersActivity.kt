package com.quandoo.androidtask.ui.customers

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.quandoo.androidtask.utils.Logger
import com.quandoo.androidtask.R
import com.quandoo.androidtask.data.models.Customer
import com.quandoo.androidtask.data.models.Reservation
import com.quandoo.androidtask.data.models.Table
import com.quandoo.androidtask.ui.tables.TablesActivity
import kotlinx.android.synthetic.main.activity_customers.*
import java.lang.RuntimeException

class CustomersActivity : AppCompatActivity(), Logger {

    private var selectedTableId: Long = NON_EXISTING_TABLE_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customers)
        recycler_view.layoutManager = LinearLayoutManager(this)

        selectedTableId = intent.getLongExtra(EXTRA_TABLE_ID, -1)

        if (this.selectedTableId == NON_EXISTING_TABLE_ID) {
            throw RuntimeException("Selected table ID cannot be found !")
        }

        recycler_view.adapter = CustomersRvAdapter(TablesActivity.customers, object : CustomersRvAdapter.CustomerClickListener {
            override fun onCustomerClick(customer: Customer) {
                log("customer clicked $customer")

                //Reserve table
                TablesActivity.tables.find { table -> table.id == selectedTableId }?.let {
                    //create reservation
                    TablesActivity.reservations.add(Reservation(customer.id, it.id, customer.id + it.id))
                    it.reservedBy = customer.firstName + " " + customer.lastName
                }

                TablesActivity.syncReservations(TablesActivity.reservations)

                finish()
            }
        })
    }

    companion object {

        const val EXTRA_TABLE_ID = "SELECTED_TABLE_ID"
        const val NON_EXISTING_TABLE_ID = -1L

        @JvmStatic
        fun createStartingIntent(clickedTable: Table, context: Context): Intent {
            val intent = Intent(context, CustomersActivity::class.java)
            intent.putExtra(EXTRA_TABLE_ID, clickedTable.id)
            return intent
        }
    }
}