package com.alexeyreznik.nytimesreader.presentation.views

import android.support.v4.content.res.ResourcesCompat
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.alexeyreznik.nytimesreader.R
import com.alexeyreznik.nytimesreader.data.Story
import com.alexeyreznik.nytimesreader.utils.calculateStoryAge
import com.bumptech.glide.Glide
import io.reactivex.subjects.PublishSubject

/**
 * Created by alexeyreznik on 12/01/2018.
 */
class StoriesListAdapter : RecyclerView.Adapter<StoriesListAdapter.ViewHolder>() {

    var stories: MutableList<Story> = mutableListOf()
    val onClickSubject: PublishSubject<Pair<Story, View?>> = PublishSubject.create()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val story = stories[position]
        holder.title.text = story.title
        holder.byline.text = story.byline
        holder.age.text = calculateStoryAge(holder.itemView.context, story.publishedDate)
        if (story.multimedia.size > 1) {
            Glide.with(holder.itemView.context).load(story.multimedia[1].url).into(holder.image)
        } else {
            holder.image.setImageDrawable(ResourcesCompat.getDrawable(holder.itemView.context.resources,
                    R.drawable.image_placeholder, null))
        }
        holder.card.setOnClickListener { onClickSubject.onNext(Pair(story, holder.image)) }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent?.context)
                .inflate(R.layout.story_card, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int
            = stories.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var card: CardView = itemView.findViewById(R.id.card)
        var image: ImageView = itemView.findViewById(R.id.image)
        var title: TextView = itemView.findViewById(R.id.title)
        var byline: TextView = itemView.findViewById(R.id.byline)
        var age: TextView = itemView.findViewById(R.id.age)
    }
}