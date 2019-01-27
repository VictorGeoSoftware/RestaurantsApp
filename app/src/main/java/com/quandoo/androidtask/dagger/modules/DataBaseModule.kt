package com.quandoo.androidtask.dagger.modules

import android.arch.persistence.room.Room
import android.content.Context
import com.quandoo.androidtask.data.room.AppDataBase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
open class DataBaseModule {

    @Provides
    @Singleton
    open fun provideAppDataBase(context: Context): AppDataBase
            = Room.databaseBuilder(context, AppDataBase::class.java, "app_db")
            .fallbackToDestructiveMigration()
            .build()
}