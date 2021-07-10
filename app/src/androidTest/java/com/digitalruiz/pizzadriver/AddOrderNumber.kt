package com.digitalruiz.pizzadriver


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest

import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class AddOrderNumber {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun addOrderNumber() {
        val imageButton = onView(
allOf(withId(R.id.add),
withParent(withParent(withId(android.R.id.content))),
isDisplayed()))
        imageButton.check(matches(isDisplayed()))
        
        val floatingActionButton = onView(
allOf(withId(R.id.add),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
1),
isDisplayed()))
        floatingActionButton.perform(click())
        
        val appCompatEditText = onView(
allOf(withId(R.id.orderNumber),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
0),
isDisplayed()))
        appCompatEditText.perform(replaceText("100"), closeSoftKeyboard())
        
        val appCompatEditText2 = onView(
allOf(withId(R.id.orderNumber), withText("100"),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
0),
isDisplayed()))
        appCompatEditText2.perform(pressImeActionButton())
        
        val button = onView(
allOf(withId(R.id.orderNumberChip), withText("100"),
withParent(withParent(withId(android.R.id.content))),
isDisplayed()))
        button.check(matches(isDisplayed()))
        
        val button2 = onView(
allOf(withId(R.id.saveButton), withText("SAVE"),
withParent(withParent(withId(android.R.id.content))),
isDisplayed()))
        button2.check(matches(isDisplayed()))
        }
    
    private fun childAtPosition(
            parentMatcher: Matcher<View>, position: Int): Matcher<View> {

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
