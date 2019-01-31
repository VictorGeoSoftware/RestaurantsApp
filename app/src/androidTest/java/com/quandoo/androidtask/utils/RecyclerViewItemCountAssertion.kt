package com.quandoo.androidtask.utils

import android.support.test.espresso.NoMatchingViewException
import android.support.test.espresso.ViewAssertion
import android.support.test.espresso.matcher.ViewMatchers
import android.support.v7.widget.RecyclerView
import android.view.View
import org.hamcrest.CoreMatchers
import org.hamcrest.Matcher

/**
 * Created by victorpalmacarrasco on 29/8/18.
 * ${APP_NAME}
 */
class RecyclerViewItemCountAssertion(var matcher: Matcher<Int>) : ViewAssertion {


    companion object {
        fun withMyItemCount(expectedCount: Int): RecyclerViewItemCountAssertion {
            return withItemCount(CoreMatchers.`is`(expectedCount))
        }

        fun withItemCount(matcher: Matcher<Int>): RecyclerViewItemCountAssertion {
            return RecyclerViewItemCountAssertion(matcher)
        }
    }



    override fun check(view: View?, noViewFoundException: NoMatchingViewException?) {
        if (noViewFoundException != null) {
            throw noViewFoundException
        }

        val recyclerView = view as RecyclerView
        val adapter = recyclerView.adapter
        ViewMatchers.assertThat(adapter?.itemCount, matcher)
    }

}