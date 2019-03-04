package com.quandoo.androidtask.dagger

import com.quandoo.androidtask.api.RestaurantService
import com.quandoo.androidtask.dagger.modules.DataManagerModule
import com.quandoo.androidtask.data.DataManager
import com.quandoo.androidtask.data.room.AppDataBase


class TestDataManagerModule: DataManagerModule() {

    override fun provideDataManager(restaurantService: RestaurantService, appDataBase: AppDataBase): DataManager {
        return super.provideDataManager(restaurantService, appDataBase)
    }
}