package com.quandoo.androidtask

import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.quandoo.androidtask.api.RestaurantService
import com.quandoo.androidtask.data.DataManager
import com.quandoo.androidtask.data.models.Customer
import com.quandoo.androidtask.data.models.Table
import com.quandoo.androidtask.presenter.TablesPresenter
import com.quandoo.androidtask.utils.getMockCustomerList
import com.quandoo.androidtask.utils.getMockReservationList
import com.quandoo.androidtask.utils.getMockTableList
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.TestScheduler
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.util.concurrent.TimeoutException

class TablesPresenterTest: ParentUnitTest() {

    @Mock lateinit var dataManager: DataManager
    @Mock lateinit var restaurantService: RestaurantService
    @Mock lateinit var tablesView: TablesPresenter.TablesView

    private lateinit var testScheduler: TestScheduler
    private lateinit var tablesPresenter: TablesPresenter


    override fun setUp() {
        super.setUp()

        testNetworkComponent.inject(this)
        MockitoAnnotations.initMocks(this)
        testScheduler = TestScheduler()
        tablesPresenter = createMockedPresenter()
    }

    override fun <T> createMockedPresenter(): T {
        val tablesPresenter = TablesPresenter(testScheduler, testScheduler, dataManager)
        tablesPresenter.view = tablesView
        return tablesPresenter as T
    }



    @Test
    fun `should retrieve a whole list from API`() {
        val tablesList = getMockTableList()
        whenever(dataManager.retrieveTablesFromServer()).thenReturn(Observable.just(tablesList))

        tablesPresenter.retrieveTablesFromServer()
        testScheduler.triggerActions()

        verify(tablesView, times(1)).onTablesListReceived(tablesList)
    }

    @Test
    fun `should call to the api and have some error`() {
        val exception = Throwable(TimeoutException())
        whenever(dataManager.retrieveTablesFromServer()).thenReturn(Observable.error(exception))

        tablesPresenter.retrieveTablesFromServer()
        testScheduler.triggerActions()

        verify(tablesView, times(1)).onTablesListError(exception)
    }

    @Test
    fun `get all data for first time and have a correct response`() {
        val mockedTableList = getMockTableList()
        whenever(dataManager.getAllTablesFromDB()).thenReturn(Observable.just(ArrayList()))
        whenever(dataManager.retrieveTablesFromServer()).thenReturn(Observable.just(mockedTableList))
        whenever(dataManager.getAllTables()).thenReturn(Observable.just(mockedTableList))

        val mockedCustomerList = getMockCustomerList()
        whenever(dataManager.getAllCustomersFromDB()).thenReturn(Observable.just(ArrayList()))
        whenever(dataManager.retrieveCustomersFromServer()).thenReturn(Observable.just(mockedCustomerList))
        whenever(dataManager.getAllCustomers()).thenReturn(Observable.just(mockedCustomerList))

        val mockedReservationList = getMockReservationList()
        whenever(dataManager.getAllReservationsFromDB()).thenReturn(Observable.just(ArrayList()))
        whenever(dataManager.retrieveReservationsFromServer()).thenReturn(Observable.just(mockedReservationList))
        whenever(dataManager.getAllReservations()).thenReturn(Observable.just(mockedReservationList))

        whenever(dataManager.loadAllData()).thenReturn(Completable.complete())

        tablesPresenter.loadAllData()
        testScheduler.triggerActions()

        verify(tablesView, times(1)).allDataIsLoaded()
    }

    @Test
    fun `retrieve all stored tables and show them up in tables activity list`() {
        val mockedTableList = getMockTableList()
        whenever(dataManager.getAllTables()).thenReturn(Observable.just(mockedTableList))

        // todo :: this method has to include casting with reservation list!
        tablesPresenter.getUpdatedTableList()
    }
}