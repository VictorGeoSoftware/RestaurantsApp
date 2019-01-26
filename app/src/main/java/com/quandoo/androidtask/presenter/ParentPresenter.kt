package com.quandoo.androidtask.presenter

import io.reactivex.disposables.CompositeDisposable

abstract class ParentPresenter<T1> {
    var view: T1? = null
    val disposable = CompositeDisposable()

    open fun destroy() {
        view = null;
    }
}