package com.quandoo.androidtask.dagger.components

import android.app.Application
import com.quandoo.androidtask.dagger.modules.AppModule
import com.quandoo.androidtask.dagger.modules.DataManagerModule
import com.quandoo.androidtask.dagger.modules.NetworkModule
import com.quandoo.androidtask.dagger.modules.PresenterModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class, DataManagerModule::class])
interface AppComponent {
    fun inject(application: Application)
    fun plus(presenterModule: PresenterModule): PresenterComponent
}