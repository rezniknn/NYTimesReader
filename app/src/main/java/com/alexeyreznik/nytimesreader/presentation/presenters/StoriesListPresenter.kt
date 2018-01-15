package com.alexeyreznik.nytimesreader.presentation.presenters

import android.view.View
import com.alexeyreznik.nytimesreader.data.Sections
import com.alexeyreznik.nytimesreader.data.Story
import com.alexeyreznik.nytimesreader.domain.GetStoriesListUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

/**
 * Created by alexeyreznik on 12/01/2018.
 */
class StoriesListPresenter(private val getStoriesListUseCase: GetStoriesListUseCase) : BasePresenter<StoriesListPresenter.StoriesListView>() {

    var section: String = Sections.HOME.title
        set(value) {
            field = value
            getStoriesList()
        }

    fun getStoriesList() {
        view?.showProgress(true)
        disposable.add(
                getStoriesListUseCase.execute(section)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ stories ->
                            view?.showProgress(false)
                            view?.showStoriesList(stories)
                        }, {
                            view?.showProgress(false)
                            view?.showError()
                        })
        )
    }

    fun registerStoryOnClickSubject(onClickSubject: PublishSubject<Pair<Story, View?>>) {
        disposable.add(
                onClickSubject.subscribe { pair -> view?.navigateToStoryDetails(pair.first, pair.second) }
        )
    }

    fun registerSectionOnClickSubject(onClickSubject: PublishSubject<String>) {
        disposable.add(
                onClickSubject.subscribe { section -> this.section = section }
        )
    }

    interface StoriesListView {
        fun showProgress(progress: Boolean)
        fun showError()
        fun showStoriesList(stories: List<Story>)
        fun navigateToStoryDetails(story: Story, sharedView: View?)
    }
}