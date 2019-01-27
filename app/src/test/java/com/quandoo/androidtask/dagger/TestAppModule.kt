package com.quandoo.androidtask.dagger

import android.app.Application
import android.content.Context
import com.quandoo.androidtask.dagger.modules.AppModule
import org.mockito.Mockito

class TestAppModule: AppModule(Mockito.mock(Application::class.java)) {
    override fun provideApplicationContext(): Context {
        return Mockito.mock(Context::class.java)
    }
}