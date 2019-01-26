package com.quandoo.androidtask.presenter

import com.quandoo.androidtask.data.DataManager
import io.reactivex.Scheduler
import javax.inject.Inject

class TablesPresenter @Inject constructor(
        private val androidThread: Scheduler,
        private val ioThread: Scheduler,
        private val dataManager: DataManager): ParentPresenter<TablesPresenter.TablesView>() {


    interface TablesView {

    }


    override fun destroy() {
        super.destroy()
        disposable.clear()
    }
}