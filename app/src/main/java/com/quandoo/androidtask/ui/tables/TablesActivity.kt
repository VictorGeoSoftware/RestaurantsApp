package com.quandoo.androidtask.ui.tables

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.quandoo.androidtask.App
import com.quandoo.androidtask.R
import com.quandoo.androidtask.data.models.Customer
import com.quandoo.androidtask.data.models.Table
import com.quandoo.androidtask.presenter.TablesPresenter
import com.quandoo.androidtask.ui.customers.CustomersActivity
import com.quandoo.androidtask.utils.AppStatus
import com.quandoo.androidtask.utils.Logger
import com.quandoo.androidtask.utils.PersistanceUtil
import com.quandoo.androidtask.utils.showRequestErrorMessage
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import javax.inject.Inject

open class TablesActivity : AppCompatActivity(), Logger, TablesRvAdapter.TableClickListener, TablesPresenter.TablesView {
    companion object {
        val TABLES_FILE_NAME = "mTablesList.bak"
        val RESERVATIONS_FILE_NAME = "reservations.bak"
        val CUSTOMERS_FILE_NAME = "customers.bak"
    }


    @Inject lateinit var tablesPresenter: TablesPresenter
    private var mTablesList = ArrayList<Table>()
    private lateinit var mTablesAdapter: TablesRvAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as App).createPresenterComponent().inject(this)
        setContentView(R.layout.activity_main)
        title = getString(R.string.tables)

        // 1.- retrieve table list
            // 1.- check if DataBase is empty
            // 1.- call to API

        // 2.- retrieve data
            // 2.- populate data on view

        // 3.- check whose mTablesList are reserved

        // Include- no internet connection method -> todo:: maybe in on error event

        mTablesAdapter = TablesRvAdapter(mTablesList, this)
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = mTablesAdapter


        tablesPresenter.view = this
        tablesPresenter.retrieveTablesFromServer()
    }

    override fun onResume() {
        super.onResume()
        syncTables()
    }

    override fun onDestroy() {
        super.onDestroy()
        (application as App).releasePresenterComponent()
    }


    override fun onTableItemClick(clickedTable: Table) {
        // todo:: use reservations db for checking table availability
        if (clickedTable.reservedBy != null) {

            //show dialog that removes the reservation
            val builder = AlertDialog.Builder(this@TablesActivity)
            builder.setMessage(getString(R.string.reserve_table_message))
                    .setPositiveButton(getText(R.string.yes)) { dialog, which ->

                        //Free table
                        // todo:: use reservations db, deleting/including a new one
                        clickedTable.reservedBy = null
                        syncTables()

                    }.setNegativeButton("No", null).show()
        } else {
            startActivity(CustomersActivity
                    .createStartingIntent(clickedTable, this@TablesActivity))
        }
    }


    override fun onTablesListReceived(tablesList: List<Table>) {
        mTablesList.clear()
        mTablesList.addAll(tablesList)
        mTablesAdapter.notifyDataSetChanged()
    }

    override fun onTablesListError(exception: Throwable) {
        showRequestErrorMessage(exception.message ?: getString(R.string.some_error_happen))
    }


    private fun writeCustomersToFile(customers: List<Customer>) {
        PersistanceUtil.saveSerializable(ArrayList(customers), CUSTOMERS_FILE_NAME)
    }


    private fun syncTables() {
        // FIXME : >:) Muhahahahaha
        // TODO :: keep just in case, but it would not be necessary
        mTablesAdapter.notifyDataSetChanged()
    }

    private fun showNoConnectionDialog() {
        if (!AppStatus.getInstance(applicationContext).isOnline) {
            val builder = AlertDialog.Builder(this)
            builder.setMessage("No internet connection!")
                    .setCancelable(false)
                    .setPositiveButton("Close App") { dialog, id -> finish() }
            val alert = builder.create()
            alert.show()
        }
    }

    open fun getFirstAvailableTable(): Int {
        return mTablesAdapter.getAllItems().indexOfFirst { table -> table.reservedBy == null }
    }

    open fun getFirstReservedTable(): Int {
        return mTablesAdapter.getAllItems().indexOfFirst { table -> table.reservedBy != null }
    }
}