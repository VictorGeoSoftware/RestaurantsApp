package com.quandoo.androidtask.dagger

import android.content.Context
import com.quandoo.androidtask.dagger.modules.DataBaseModule
import com.quandoo.androidtask.data.room.AppDataBase
import org.mockito.Mockito

class TestDataBaseModule: DataBaseModule() {

    override fun provideAppDataBase(context: Context): AppDataBase {
        return Mockito.mock(AppDataBase::class.java)
    }
}