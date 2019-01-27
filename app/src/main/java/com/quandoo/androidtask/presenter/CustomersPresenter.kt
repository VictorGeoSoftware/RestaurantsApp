package com.quandoo.androidtask.presenter

import com.quandoo.androidtask.data.DataManager
import com.quandoo.androidtask.data.models.Customer
import io.reactivex.Scheduler
import javax.inject.Inject

class CustomersPresenter @Inject constructor(
        private val androidThread: Scheduler,
        private val ioThread: Scheduler,
        private val dataManager: DataManager): ParentPresenter<CustomersPresenter.CustomersView>() {


    interface CustomersView {

    }


    fun reserveTable(selectedTableId: Long, customer: Customer) {
        dataManager.reserveTable(selectedTableId, customer)
    }

    override fun destroy() {
        super.destroy()
        disposable.clear()
    }


}