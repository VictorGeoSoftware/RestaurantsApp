package com.quandoo.androidtask.presenter

import com.quandoo.androidtask.data.DataManager
import com.quandoo.androidtask.data.models.Table
import io.reactivex.Scheduler
import javax.inject.Inject

class TablesPresenter @Inject constructor(
        private val androidThread: Scheduler,
        private val ioThread: Scheduler,
        private val dataManager: DataManager): ParentPresenter<TablesPresenter.TablesView>() {


    interface TablesView {
        fun onTablesListReceived(tablesList: List<Table>)
        fun onTablesListError(exception: Throwable)
        fun allDataIsLoaded()
        fun errorLoadingAllData()

    }

    fun loadAllData() {
        // the logic has to be first check to DB and then retrieve from API
        disposable.add(dataManager.loadAllData()
                .observeOn(androidThread)
                .subscribeOn(ioThread)
                .subscribe({
                    // after this interface all necessary data has to be requested
                    view?.allDataIsLoaded()
                }, {
                    view?.errorLoadingAllData()
                }))
    }

    fun getUpdatedTableList() {
        disposable.add(dataManager.getUpdatedTableList()
                .observeOn(androidThread)
                .subscribeOn(ioThread)
                .subscribe({
                    view?.onTablesListReceived(it)
                }, {
                    view?.onTablesListError(it)
                }))
    }

    fun deleteReservation(clickedTable: Table) {
        dataManager.deleteReservationAndGetUpdatedList(clickedTable)
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