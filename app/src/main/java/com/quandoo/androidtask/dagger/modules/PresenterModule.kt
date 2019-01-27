package com.quandoo.androidtask.dagger.modules

import com.quandoo.androidtask.dagger.scopes.ViewScope
import com.quandoo.androidtask.data.DataManager
import com.quandoo.androidtask.data.room.AppDataBase
import com.quandoo.androidtask.presenter.CustomersPresenter
import com.quandoo.androidtask.presenter.TablesPresenter
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Named

@Module
class PresenterModule {
    companion object {
        const val ANDROID_SCHEDULER = "ANDROID_SCHEDULER"
        const val TASK_SCHEDULER = "TASK_SCHEDULER"
    }


    @Provides
    @Named(ANDROID_SCHEDULER)
    fun provideAndroidScheduler(): Scheduler = AndroidSchedulers.mainThread()

    @Provides
    @Named(TASK_SCHEDULER)
    fun provideTaskScheduler(): Scheduler = Schedulers.newThread()


    @Provides
    @ViewScope
    fun provideCustomersPresenter(@Named(ANDROID_SCHEDULER) androidScheduler:Scheduler,
                                  @Named(TASK_SCHEDULER) taskScheduler:Scheduler,
                                  dataManager: DataManager)
            = CustomersPresenter(androidScheduler, taskScheduler, dataManager)

    @Provides
    @ViewScope
    fun provideTablesPresenter(@Named(ANDROID_SCHEDULER) androidScheduler:Scheduler,
                               @Named(TASK_SCHEDULER) taskScheduler:Scheduler,
                               dataManager: DataManager)
            = TablesPresenter(androidScheduler, taskScheduler, dataManager)
}