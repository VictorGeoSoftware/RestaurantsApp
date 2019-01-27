package com.quandoo.androidtask.dagger.modules

import com.quandoo.androidtask.api.RestaurantService
import com.quandoo.androidtask.data.DataManager
import com.quandoo.androidtask.data.room.AppDataBase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataManagerModule {

    @Provides
    @Singleton
    open fun provideDataManager(restaurantService: RestaurantService, appDataBase: AppDataBase)
            = DataManager(restaurantService, appDataBase)
}