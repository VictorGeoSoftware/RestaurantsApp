package com.quandoo.androidtask.dagger.components

import com.quandoo.androidtask.dagger.modules.NetworkModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface NetworkComponent