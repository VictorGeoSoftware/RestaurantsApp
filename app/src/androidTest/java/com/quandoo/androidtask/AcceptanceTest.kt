package com.quandoo.androidtask

import android.content.Intent
import android.os.AsyncTask
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.quandoo.androidtask.ui.tables.TablesActivity
import com.quandoo.androidtask.ui.tables.TablesActivity.Companion.CUSTOMERS_FILE_NAME
import com.quandoo.androidtask.ui.tables.TablesActivity.Companion.RESERVATIONS_FILE_NAME
import com.quandoo.androidtask.ui.tables.TablesActivity.Companion.TABLES_FILE_NAME
import com.quandoo.androidtask.utils.EspressoCustomMarchers.Companion.first
import com.quandoo.androidtask.utils.EspressoCustomMarchers.Companion.withHolderTablesView
import com.quandoo.androidtask.utils.EspressoCustomMarchers.Companion.withRecyclerView
import com.quandoo.androidtask.utils.PersistanceUtil
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.hamcrest.CoreMatchers.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class AcceptanceTest {

    @Rule
    @JvmField
    var mActivityTestRule: ActivityTestRule<TablesActivity> =
            ActivityTestRule(TablesActivity::class.java, true,
                    false)
    lateinit var mTablesActivity: TablesActivity

    @Before
    fun setup() {

        //clear cached state
        PersistanceUtil.removeSerializable(TABLES_FILE_NAME)
        PersistanceUtil.removeSerializable(CUSTOMERS_FILE_NAME)
        PersistanceUtil.removeSerializable(RESERVATIONS_FILE_NAME)

        //make espresso wait for RXJava
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.from(AsyncTask.THREAD_POOL_EXECUTOR) }
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.from(AsyncTask.THREAD_POOL_EXECUTOR) }
        RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.from(AsyncTask.THREAD_POOL_EXECUTOR) }


        //launch activity using empty intent (no arguments needed for now ...)
        mActivityTestRule.launchActivity(Intent())
        mTablesActivity = mActivityTestRule.activity
    }

    @After
    fun tearDown() {
    }


    @Test
    fun reserveTableTest() {

        //GIVEN :

        //App is open
        onView(withText("Tables")).check(matches(isDisplayed()))

        // List of tables visible
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()))

        //There is at least one free table visible
        onView(withId(R.id.recycler_view))
                .perform(RecyclerViewActions.scrollToHolder(first(withHolderTablesView("Free"))))


        //Hacky way of getting a position of desired element

//        val freeTablePosition = TablesActivity.tables.indexOfFirst { table -> table.reservedBy == null }
        val freeTablePosition = mTablesActivity.getFirstAvailableTable()

        //WHEN :

        //User clicks on free table
        onView(withId(R.id.recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(freeTablePosition, click()))

        //THEN :

        //Screen with customers appear
        onView(withText("Customers")).check(matches(isDisplayed()))

        //WHEN :

        //User clicks on any user
        onView(withId(R.id.recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(freeTablePosition, click()))

        //THEN :

        //Screen with users tables appear
        onView(withText("Tables")).check(matches(isDisplayed()))


        //Previously selected table is marked as reserved by a user name
        onView(withRecyclerView(R.id.recycler_view).atPosition(freeTablePosition))
                .check(matches(not(hasDescendant(withText("Free")))))

    }

    @Test
    fun removeReservationTest() {

        //GIVEN :

        //App is open
        onView(withText("Tables")).check(matches(isDisplayed()))

        // List of tables visible
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()))

        //There is at least one reserved table
        onView(withId(R.id.recycler_view))
                .perform(RecyclerViewActions.scrollToHolder(first(not(withHolderTablesView("Free")))))


//        val reservedTablePosition = TablesActivity.tables.indexOfFirst { table -> table.reservedBy != null }
        val reservedTablePosition = mTablesActivity.getFirstReservedTable()

        //WHEN :

        //User clicks on a reserved table
        onView(withId(R.id.recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(reservedTablePosition, click()))

        //THEN :

        //Confirmation dialog appears
        onView(withText("Do you want to free the table?")).check(matches(isDisplayed()))

        //WHEN :

        //User clicks on a accept button
        onView(withText("Yes")).check(matches(isDisplayed())).perform(click())


        //Previously reserved table is marked as free
        onView(withRecyclerView(R.id.recycler_view).atPosition(reservedTablePosition))
                .check(matches(hasDescendant(withText("Free"))))

    }

}