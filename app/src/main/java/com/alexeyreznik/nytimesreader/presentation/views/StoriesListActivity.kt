package com.alexeyreznik.nytimesreader.presentation.views

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
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
    private lateinit var adapter: StoriesListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stories_list)
        title = getString(R.string.top_stories)

        component = (application as App).component.addStoriesModule(StoriesListModule())
        presenter = component.presenter()
        presenter.attachView(this)

        initRecyclerView()
        initSectionsView()
        presenter.getStoriesList()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun showProgress(progress: Boolean) {
        srl.isRefreshing = progress
    }

    override fun showError() {
        Toast.makeText(this, getString(R.string.error_failed_to_get_stories), Toast.LENGTH_LONG).show()
    }

    override fun showStoriesList(stories: List<Story>) {
        adapter.stories.clear()
        adapter.stories.addAll(stories)
        adapter.notifyDataSetChanged()
        recycler_view.smoothScrollToPosition(0)
    }

    override fun navigateToStoryDetails(story: Story, sharedView: View?) {
        val intent = Intent(this, StoryDetailsActivity::class.java)
        intent.putExtra(StoryDetailsActivity.ARG_STORY, story)
        sharedView?.let {
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, sharedView as View, "thumbnail")
            startActivity(intent, options.toBundle())
        } ?: startActivity(intent)
    }

    private fun initRecyclerView() {
        adapter = StoriesListAdapter()
        presenter.registerStoryOnClickSubject(adapter.onClickSubject)
        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(this)
        srl.setOnRefreshListener { presenter.getStoriesList() }
    }

    private fun initSectionsView() {
        presenter.registerSectionOnClickSubject(section.onClickSubject)
    }
}
