package com.quandoo.androidtask

import android.content.res.Resources
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.quandoo.androidtask.data.DataManager
import com.quandoo.androidtask.data.models.Customer
import com.quandoo.androidtask.data.room.AppDataBase
import com.quandoo.androidtask.presenter.CustomersPresenter
import io.reactivex.Completable
import io.reactivex.schedulers.TestScheduler
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CustomersPresenterTest: ParentUnitTest() {
    @Mock lateinit var dataManager: DataManager
    @Mock lateinit var customersView: CustomersPresenter.CustomersView

    private lateinit var testScheduler: TestScheduler
    private lateinit var customersPresenter: CustomersPresenter


    override fun setUp() {
        super.setUp()

        testNetworkComponent.inject(this)
        MockitoAnnotations.initMocks(this)
        testScheduler = TestScheduler()
        customersPresenter = createMockedPresenter()
    }

    override fun <T> createMockedPresenter(): T {
        val customersPresenter = CustomersPresenter(testScheduler, testScheduler, dataManager)
        customersPresenter.view = customersView
        return customersPresenter as T
    }



    @Test
    fun `reserve table and have any table with provided tableId`() {
        val unFoundTableId = 2777L
        val sadCostumer = Customer()
        sadCostumer.id = 1
        sadCostumer.firstName = "Victor"
        sadCostumer.lastName= "Palma"
        sadCostumer.imageUrl = ""

        whenever(dataManager.reserveTable(unFoundTableId, sadCostumer)).thenReturn(Completable.error(Resources.NotFoundException("")))

        customersPresenter.reserveTable(unFoundTableId, sadCostumer)
        testScheduler.triggerActions()

        verify(customersView, times(1)).onTableReservingError()
    }

    @Test
    fun `reserve table and complete all the process successful`() {
        val unFoundTableId = 2777L
        val sadCostumer = Customer()
        sadCostumer.id = 1
        sadCostumer.firstName = "Victor"
        sadCostumer.lastName= "Palma"
        sadCostumer.imageUrl = ""

        whenever(dataManager.reserveTable(unFoundTableId, sadCostumer)).thenReturn(Completable.complete())

        customersPresenter.reserveTable(unFoundTableId, sadCostumer)
        testScheduler.triggerActions()

        verify(customersView, times(1)).onTableReserved()
    }
}