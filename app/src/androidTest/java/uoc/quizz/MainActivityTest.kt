package uoc.quizz

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest{

    @Rule @JvmField
    var activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun scenario1() {
        //not select any radio button and click
        onView(withId(R.id.button)).perform(click())

        // select wrong option and click
        onView(withId(R.id.radioButton2)).perform(click())
        onView(withId(R.id.button)).perform(click())
        onView(withText(R.string.failed)).check(matches(isDisplayed()))
    }

    @Test
    fun scenario2() {
        // select success option and click
        onView(withId(R.id.radioButton1)).perform(click())
        onView(withId(R.id.button)).perform(click())
        onView(withText(R.string.rigth)).check(matches(isDisplayed()))
    }


    @Test
    fun scenario3() {
        // select success option click and complete the quizz
        onView(withId(R.id.radioButton1)).perform(click())
        onView(withId(R.id.button)).perform(click())
        onView(withId(R.id.againBtn)).perform(click())
        onView(withId(R.id.radioButton2)).perform(click())
        onView(withId(R.id.button)).perform(click())
        onView(withId(R.id.againBtn)).perform(click())
        onView(withId(R.id.radioButton1)).perform(click())
        onView(withId(R.id.button)).perform(click())
        onView(withText(R.string.congrats)).check(matches(isDisplayed()))
    }


}