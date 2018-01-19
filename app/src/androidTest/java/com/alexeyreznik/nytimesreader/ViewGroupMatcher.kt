package com.alexeyreznik.nytimesreader

import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher


/**
 * Created by alexeyreznik on 19/01/2018.
 */
fun hasChildren(numChildrenMatcher: Matcher<Int>): Matcher<View> {
    return object : TypeSafeMatcher<View>() {

        /**
         * matching with viewgroup.getChildCount()
         */
        override fun matchesSafely(view: View): Boolean {
            return view is ViewGroup && numChildrenMatcher.matches((view as ViewGroup).childCount)
        }

        /**
         * gets the description
         */
        override fun describeTo(description: Description) {
            description.appendText(" a view with # children is ")
            numChildrenMatcher.describeTo(description)
        }
    }
}

fun nthChildOf(parentMatcher: Matcher<View>, childPosition: Int): Matcher<View> {
    return object : TypeSafeMatcher<View>() {
        override fun describeTo(description: Description) {
            description.appendText("position $childPosition of parent ")
            parentMatcher.describeTo(description)
        }

        public override fun matchesSafely(view: View): Boolean {
            if (view.parent !is ViewGroup) return false
            val parent = view.parent as ViewGroup

            return (parentMatcher.matches(parent)
                    && parent.childCount > childPosition
                    && parent.getChildAt(childPosition) == view)
        }
    }
}