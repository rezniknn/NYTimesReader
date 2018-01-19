package com.alexeyreznik.nytimesreader.presentation.views

import android.os.Bundle
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.app.AppCompatActivity
import com.alexeyreznik.nytimesreader.R
import com.alexeyreznik.nytimesreader.data.Story
import com.alexeyreznik.nytimesreader.utils.ResourcesWrapper
import com.alexeyreznik.nytimesreader.utils.calculateStoryAge
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_story_details.*
import java.util.*

class StoryDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story_details)
        title = getString(R.string.story_details)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val story = intent.getParcelableExtra<Story>(ARG_STORY)
        story?.let {
            story_title.text = it.title
            byline.text = it.byline
            abstrct.text = it.abstract
            link.text = it.url
            age.text = calculateStoryAge(ResourcesWrapper(this), it.publishedDate, Date())
            if (story.multimedia.size > 1) {
                Glide.with(this).load(story.multimedia[1].url).into(image)
            } else {
                image.setImageDrawable(ResourcesCompat.getDrawable(resources,
                        R.drawable.image_placeholder, null))
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        supportFinishAfterTransition()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        val ARG_STORY = "story"
    }
}
