package com.quandoo.androidtask.dagger.modules

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
open class AppModule(private val application: Application) {

    @Provides
    @Singleton
    open fun provideApplicationContext(): Context = application
}