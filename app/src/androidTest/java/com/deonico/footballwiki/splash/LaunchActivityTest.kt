package com.deonico.footballwiki.splash


import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.View
import android.view.ViewGroup
import com.deonico.footballwiki.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class LaunchActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(LaunchActivity::class.java)

    @Test
    fun launchActivityTest() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(5000)

        Thread.sleep(2000)

        val actionMenuItemView = onView(
            allOf(
                withId(R.id.searchMenu), withContentDescription("Search"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.action_bar),
                        1
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        actionMenuItemView.perform(click())

        Thread.sleep(1000)

        val searchAutoComplete = onView(
            allOf(
                withId(R.id.search_src_text),
                childAtPosition(
                    allOf(
                        withId(R.id.search_plate),
                        childAtPosition(
                            withId(R.id.search_edit_frame),
                            1
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        searchAutoComplete.perform(replaceText("Bayern"), closeSoftKeyboard())

        Thread.sleep(2000)

        val cardView = onView(
            allOf(
                childAtPosition(
                    allOf(
                        withId(R.id.rv_matches_list),
                        childAtPosition(
                            withClassName(`is`("android.support.v4.widget.SwipeRefreshLayout")),
                            0
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        cardView.perform(click())

        val actionMenuItemView2 = onView(
            allOf(
                withId(R.id.add_to_favorite), withContentDescription("Favorites"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.action_bar),
                        2
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        actionMenuItemView2.perform(click())

        pressBack()

        pressBack()

        pressBack()

        val bottomNavigationItemView = onView(
            allOf(
                withId(R.id.navigation_teams), withContentDescription("Teams"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_button),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView.perform(click())

        val actionMenuItemView3 = onView(
            allOf(
                withId(R.id.searchMenu1), withContentDescription("Search"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.action_bar),
                        1
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        actionMenuItemView3.perform(click())

        Thread.sleep(1000)

        val searchAutoComplete1 = onView(
            allOf(
                withId(R.id.search_src_text),
                childAtPosition(
                    allOf(
                        withId(R.id.search_plate),
                        childAtPosition(
                            withId(R.id.search_edit_frame),
                            1
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        searchAutoComplete1.perform(replaceText("Juventus"), closeSoftKeyboard())

        Thread.sleep(2000)

        val cardView2 = onView(
            allOf(
                childAtPosition(
                    allOf(
                        withId(R.id.rv_matches_list),
                        childAtPosition(
                            withClassName(`is`("android.support.v4.widget.SwipeRefreshLayout")),
                            0
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        cardView2.perform(click())

        val actionMenuItemView4 = onView(
            allOf(
                withId(R.id.add_to_favorite), withContentDescription("Favorites"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.action_bar),
                        2
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        actionMenuItemView4.perform(click())

        val tabView = onView(
            allOf(
                withContentDescription("PLAYERS"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.team_detail_tabs),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        tabView.perform(click())

        Thread.sleep(2000)

        pressBack()

        pressBack()

        pressBack()

        val bottomNavigationItemView2 = onView(
            allOf(
                withId(R.id.navigation_favorite), withContentDescription("Favorites"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_button),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView2.perform(click())

        val tabView2 = onView(
            allOf(
                withContentDescription("Teams"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.tabLayout),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        tabView2.perform(click())

        val viewPager2 = onView(
            allOf(
                withId(R.id.viewPager),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.content_main),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        viewPager2.perform(swipeLeft())

        val tabView3 = onView(
            allOf(
                withContentDescription("Players"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.tabLayout),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        tabView3.perform(click())

        val viewPager3 = onView(
            allOf(
                withId(R.id.viewPager),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.content_main),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        viewPager3.perform(swipeLeft())
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
