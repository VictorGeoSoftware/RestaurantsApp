package com.quandoo.androidtask.ui.customers

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.quandoo.androidtask.App
import com.quandoo.androidtask.R
import com.quandoo.androidtask.data.models.Customer
import com.quandoo.androidtask.data.models.Reservation
import com.quandoo.androidtask.data.models.Table
import com.quandoo.androidtask.presenter.CustomersPresenter
import com.quandoo.androidtask.ui.tables.TablesActivity
import com.quandoo.androidtask.utils.Logger
import kotlinx.android.synthetic.main.activity_customers.*
import javax.inject.Inject

class CustomersActivity : AppCompatActivity(), Logger, CustomersRvAdapter.CustomerClickListener {
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

    private var selectedTableId: Long = NON_EXISTING_TABLE_ID
    @Inject lateinit var customersPresenter: CustomersPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as App).createPresenterComponent().inject(this)
        setContentView(R.layout.activity_customers)


        selectedTableId = intent.getLongExtra(EXTRA_TABLE_ID, -1)


        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = CustomersRvAdapter(TablesActivity.customers, this)
    }

    override fun onDestroy() {
        super.onDestroy()
        (application as App).releasePresenterComponent()
    }


    override fun onCustomerClick(customer: Customer) {
        log("customer clicked $customer")

        // todo:: customersPresenter.reserveTable(selectedTableId, customer)
        //Reserve table
        TablesActivity.tables.find { table -> table.id == selectedTableId }?.let {
            //create reservation
            TablesActivity.reservations.add(Reservation(customer.id, it.id, customer.id + it.id))
            it.reservedBy = customer.firstName + " " + customer.lastName //update object into TablesActivity.tables
        }

        TablesActivity.syncReservations(TablesActivity.reservations)

        finish()

        customersPresenter.reserveTable(selectedTableId, customer)
    }
}