package com.quandoo.androidtask

import com.quandoo.androidtask.dagger.TestAppModule
import com.quandoo.androidtask.dagger.TestDataBaseModule
import com.quandoo.androidtask.dagger.TestDataManagerModule
import com.quandoo.androidtask.dagger.TestNetworkModule
import com.quandoo.androidtask.dagger.components.NetworkComponent
import com.quandoo.androidtask.dagger.modules.AppModule
import com.quandoo.androidtask.dagger.modules.DataBaseModule
import com.quandoo.androidtask.dagger.modules.DataManagerModule
import com.quandoo.androidtask.dagger.modules.NetworkModule
import dagger.Component
import org.junit.Before
import javax.inject.Singleton

abstract class ParentUnitTest {
    open lateinit var testNetworkComponent: TestNetworkComponent


    @Singleton
    @Component(modules = [AppModule::class, NetworkModule::class, DataManagerModule::class, DataBaseModule::class])
    interface TestNetworkComponent: NetworkComponent {
        fun inject(target: CustomersPresenterTest)
        fun inject(target: TablesPresenterTest)
    }


    @Before
    open fun setUp() {
        testNetworkComponent = DaggerParentUnitTest_TestNetworkComponent.builder()
                .appModule(TestAppModule())
                .networkModule(TestNetworkModule())
                .dataBaseModule(TestDataBaseModule())
                .dataManagerModule(TestDataManagerModule())
                .build()
    }

    protected abstract fun <T>createMockedPresenter(): T
}