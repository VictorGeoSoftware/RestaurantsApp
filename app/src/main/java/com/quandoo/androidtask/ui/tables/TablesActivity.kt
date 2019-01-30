package com.quandoo.androidtask.ui.tables

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.quandoo.androidtask.App
import com.quandoo.androidtask.R
import com.quandoo.androidtask.data.models.Table
import com.quandoo.androidtask.presenter.TablesPresenter
import com.quandoo.androidtask.ui.customers.CustomersActivity
import com.quandoo.androidtask.ui.customers.CustomersActivity.Companion.CUSTOMERS_ACTIVITY_RESULT
import com.quandoo.androidtask.utils.AppStatus
import com.quandoo.androidtask.utils.Logger
import com.quandoo.androidtask.utils.myTrace
import com.quandoo.androidtask.utils.showRequestErrorMessage
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

open class TablesActivity : AppCompatActivity(), Logger, TablesRvAdapter.TableClickListener, TablesPresenter.TablesView {
    companion object {
        val TABLES_FILE_NAME = "mTablesList.bak"
        val RESERVATIONS_FILE_NAME = "reservations.bak"
        val CUSTOMERS_FILE_NAME = "customers.bak"

        const val TABLES_ACTIVITY_INSTANCE_STATE = "TABLES_ACTIVITY_INSTANCE_STATE"
    }


    @Inject lateinit var mTablesPresenter: TablesPresenter
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

        if (savedInstanceState != null) {
            mTablesList = savedInstanceState.getParcelableArrayList(TABLES_ACTIVITY_INSTANCE_STATE) ?: ArrayList()
        } else {
            mTablesPresenter.loadAllData()
        }

        mTablesAdapter = TablesRvAdapter(mTablesList, this)
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = mTablesAdapter


        mTablesPresenter.view = this
    }

    override fun onDestroy() {
        super.onDestroy()
        (application as App).releasePresenterComponent()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putParcelableArrayList(TABLES_ACTIVITY_INSTANCE_STATE, mTablesList)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CUSTOMERS_ACTIVITY_RESULT && resultCode == Activity.RESULT_OK) {
            mTablesPresenter.getUpdatedTableList()
            myTrace("onActivityResult - update list!")
        }
    }


    override fun allDataIsLoaded() {
        mTablesPresenter.getUpdatedTableList()
    }

    override fun errorLoadingAllData() {
        showRequestErrorMessage(getString(R.string.some_error_happen))
    }

    override fun onTableItemClick(clickedTable: Table) {
        if (clickedTable.reservedBy != null) {

            //show dialog that removes the reservation
            val builder = AlertDialog.Builder(this@TablesActivity)
            builder.setMessage(getString(R.string.reserve_table_message))
                    .setPositiveButton(getText(R.string.yes)) { dialog, which ->

                        //Free table
                        mTablesPresenter.deleteReservation(clickedTable)

                    }.setNegativeButton(getString(R.string.dialog_no), null).show()
        } else {
            startActivityForResult(
                    CustomersActivity.createStartingIntent(clickedTable, this@TablesActivity),
                    CUSTOMERS_ACTIVITY_RESULT)
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


//    private fun writeCustomersToFile(customers: List<Customer>) {
//        PersistanceUtil.saveSerializable(ArrayList(customers), CUSTOMERS_FILE_NAME)
//    }


//    private fun syncTables() {
//        // FIXME : >:) Muhahahahaha
//        mTablesAdapter.notifyDataSetChanged()
//    }

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