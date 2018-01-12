package com.alexeyreznik.nytimesreader.presentation.views

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.alexeyreznik.nytimesreader.App
import com.alexeyreznik.nytimesreader.R
import com.alexeyreznik.nytimesreader.data.Story
import com.alexeyreznik.nytimesreader.di.components.StoriesListComponent
import com.alexeyreznik.nytimesreader.di.modules.StoriesListModule
import com.alexeyreznik.nytimesreader.presentation.presenters.StoriesListPresenter
import kotlinx.android.synthetic.main.activity_stories_list.*

class StoriesListActivity : AppCompatActivity(), StoriesListPresenter.StoriesListView {

    private lateinit var component: StoriesListComponent
    private lateinit var presenter: StoriesListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stories_list)

        component = (application as App).component.addStoriesModule(StoriesListModule())
        presenter = component.presenter()
        presenter.attachView(this)

        presenter.getStoriesList()
    }

    override fun showProgress(progress: Boolean) {
        srl.isRefreshing = progress
    }

    override fun showError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show()
    }

    override fun showStories(stories: List<Story>) {
        Log.d("StoriesListActivity", stories.toString())
    }

    override fun navigateToStoryDetails(story: Story, sharedView: View) {
    }
}
