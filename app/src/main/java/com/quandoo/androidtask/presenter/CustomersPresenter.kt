package com.quandoo.androidtask.presenter

import com.quandoo.androidtask.data.DataManager
import io.reactivex.Scheduler
import javax.inject.Inject

class CustomersPresenter @Inject constructor(
        private val androidThread: Scheduler,
        private val ioThread: Scheduler,
        private val dataManager: DataManager): ParentPresenter<CustomersPresenter.CustomersView>() {


    interface CustomersView {

    }


    override fun destroy() {
        super.destroy()
        disposable.clear()
    }
}