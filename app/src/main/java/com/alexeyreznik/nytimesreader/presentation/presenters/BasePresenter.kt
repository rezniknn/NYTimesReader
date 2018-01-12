package com.alexeyreznik.nytimesreader.presentation.presenters

import io.reactivex.disposables.CompositeDisposable

/**
 * Created by alexeyreznik on 12/01/2018.
 */
abstract class BasePresenter<T> {

    var disposable = CompositeDisposable()

    var view: T? = null
        private set

    fun attachView(view: T) {
        this.view = view
    }

    fun detachView() {
        this.view = null
        this.disposable.clear()
    }

    val isViewAttached: Boolean
        get() = view != null
}