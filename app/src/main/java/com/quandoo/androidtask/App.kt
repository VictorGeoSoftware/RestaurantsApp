package com.quandoo.androidtask

import android.app.Application
import com.quandoo.androidtask.dagger.components.AppComponent
import com.quandoo.androidtask.dagger.components.DaggerAppComponent
import com.quandoo.androidtask.dagger.components.PresenterComponent
import com.quandoo.androidtask.dagger.modules.AppModule
import com.quandoo.androidtask.dagger.modules.PresenterModule

class App : Application() {
    companion object {
        lateinit var instance: App
    }

    private val component: AppComponent by lazy {
        DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }

    private var presenterComponent: PresenterComponent? = null

    override fun onCreate() {
        super.onCreate()
        component.inject(this)
        instance = this
    }


    fun createPresenterComponent(): PresenterComponent {
        return component.plus(PresenterModule())
    }

    fun releasePresenterComponent() {
        presenterComponent = null
    }
}