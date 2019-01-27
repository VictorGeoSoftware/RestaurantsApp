package com.quandoo.androidtask.dagger

import com.quandoo.androidtask.api.RestaurantService
import com.quandoo.androidtask.dagger.modules.NetworkModule
import org.mockito.Mockito
import retrofit2.Retrofit

class TestNetworkModule: NetworkModule() {
    override fun provideRestaurantRepository(retrofit: Retrofit): RestaurantService {
        return super.provideRestaurantRepository(retrofit)
//        return Mockito.mock(RestaurantService::class.java)
    }
}