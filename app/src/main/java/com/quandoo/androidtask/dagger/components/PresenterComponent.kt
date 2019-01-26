package com.quandoo.androidtask.dagger.components

import com.quandoo.androidtask.dagger.modules.PresenterModule
import com.quandoo.androidtask.dagger.scopes.ViewScope
import com.quandoo.androidtask.ui.customers.CustomersActivity
import com.quandoo.androidtask.ui.tables.TablesActivity
import dagger.Subcomponent


@ViewScope
@Subcomponent(modules = [PresenterModule::class])
interface PresenterComponent {
    fun inject(target: TablesActivity)
    fun inject(target: CustomersActivity)
}