package com.quandoo.androidtask.presenter

import com.quandoo.androidtask.data.DataManager
import com.quandoo.androidtask.data.models.Table
import io.reactivex.Observable
import io.reactivex.Scheduler
import java.util.function.BiFunction
import javax.inject.Inject

class TablesPresenter @Inject constructor(
        private val androidThread: Scheduler,
        private val ioThread: Scheduler,
        private val dataManager: DataManager): ParentPresenter<TablesPresenter.TablesView>() {


    interface TablesView {
        fun onTablesListReceived(tablesList: List<Table>)
        fun onTablesListError(exception: Throwable)

    }

    fun loadAllData() {
        // todo :: complete function
    }

    fun retrieveTablesFromServer() {

        disposable.add(dataManager.retrieveTablesFromServer()
                .observeOn(androidThread)
                .subscribeOn(ioThread)
                .subscribe({
                    view?.onTablesListReceived(it)
                }, {
                    view?.onTablesListError(it)
                }))
    }

    override fun destroy() {
        super.destroy()
        disposable.clear()
    }


}