package com.alexeyreznik.nytimesreader.presentation.views

import android.content.Context
import android.support.v4.content.res.ResourcesCompat
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import android.widget.ToggleButton
import com.alexeyreznik.nytimesreader.R
import com.alexeyreznik.nytimesreader.data.Sections
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.sections_view.view.*

/**
 * Created by alexeyreznik on 13/01/2018.
 */
class SectionsView : HorizontalScrollView {

    val onClickSubject: PublishSubject<String> = PublishSubject.create()

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        LayoutInflater.from(context).inflate(R.layout.sections_view, this, true)

        for (section in Sections.values()) {
            val toggle = ToggleButton(context)
            toggle.text = section.title
            toggle.textOn = section.title
            toggle.textOff = section.title
            toggle.setAllCaps(false)
            toggle.background = ResourcesCompat.getDrawable(resources, R.drawable.section_background, null)
            toggle.setTextColor(ResourcesCompat.getColorStateList(resources, R.color.section_text_color, null))
            val params = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, resources.getDimensionPixelSize(R.dimen.section_button_height))
            val margin = resources.getDimensionPixelSize(R.dimen.section_button_margin)
            params.setMargins(margin, margin, margin, margin)
            toggle.layoutParams = params
            val padding = resources.getDimensionPixelSize(R.dimen.section_button_padding)
            toggle.setPadding(padding, 0, padding, 0)
            toggle.setOnClickListener { view -> onSectionClick(view) }
            if (Sections.values().indexOf(section) == 0) toggle.isChecked = true
            container.addView(toggle)
        }
    }

    private fun onSectionClick(view: View) {
        (0 until container.childCount)
                .map { container.getChildAt(it) as ToggleButton }
                .forEach {
                    if (it != view) {
                        if (it.isChecked) {
                            it.isChecked = false
                            it.isEnabled = true
                        }
                    } else {
                        it.isEnabled = false
                        onClickSubject.onNext(it.text.toString())
                    }
                }
    }
}