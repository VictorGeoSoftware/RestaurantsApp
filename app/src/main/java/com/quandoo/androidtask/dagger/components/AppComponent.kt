package com.quandoo.androidtask.dagger.components

import android.app.Application
import com.quandoo.androidtask.dagger.modules.*
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class, DataManagerModule::class, DataBaseModule::class])
interface AppComponent {
    fun inject(application: Application)
    fun plus(presenterModule: PresenterModule): PresenterComponent
}