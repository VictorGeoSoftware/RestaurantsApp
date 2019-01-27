package com.quandoo.androidtask

import com.quandoo.androidtask.dagger.TestNetworkModule
import com.quandoo.androidtask.dagger.components.NetworkComponent
import com.quandoo.androidtask.dagger.modules.DataManagerModule
import com.quandoo.androidtask.dagger.modules.NetworkModule
import dagger.Component
import org.junit.Before
import javax.inject.Singleton

abstract class ParentUnitTest {
    open lateinit var testNetworkComponent: TestNetworkComponent


    @Singleton
    @Component(modules = [NetworkModule::class, DataManagerModule::class])
    interface TestNetworkComponent: NetworkComponent {
        fun inject(target: CustomersPresenterTest)
    }


    @Before
    open fun setUp() {
        testNetworkComponent = DaggerParentUnitTest_TestNetworkComponent.builder()
                .networkModule(TestNetworkModule())
                .build()
    }

    protected abstract fun <T>createMockedPresenter(): T
}