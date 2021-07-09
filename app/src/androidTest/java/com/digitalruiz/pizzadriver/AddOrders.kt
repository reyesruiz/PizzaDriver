package com.digitalruiz.pizzadriver


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
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
class AddOrders {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun addOrders() {
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
        
        val chip = onView(
allOf(withId(R.id.creditAutoType), withText("Credit Auto"),
childAtPosition(
allOf(withId(R.id.orderType),
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
1)),
0),
isDisplayed()))
        chip.perform(click())
        
        val appCompatEditText3 = onView(
allOf(withId(R.id.tip),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
2),
1),
isDisplayed()))
        appCompatEditText3.perform(replaceText("5"), closeSoftKeyboard())
        
        val appCompatEditText4 = onView(
allOf(withId(R.id.tip), withText("5"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
2),
1),
isDisplayed()))
        appCompatEditText4.perform(pressImeActionButton())
        
        val materialButton = onView(
allOf(withId(R.id.saveButton), withText("Save"),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
6),
isDisplayed()))
        materialButton.perform(click())
        
        val floatingActionButton2 = onView(
allOf(withId(R.id.add),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
1),
isDisplayed()))
        floatingActionButton2.perform(click())
        
        val appCompatEditText5 = onView(
allOf(withId(R.id.orderNumber),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
0),
isDisplayed()))
        appCompatEditText5.perform(replaceText("101"), closeSoftKeyboard())
        
        val appCompatEditText6 = onView(
allOf(withId(R.id.orderNumber), withText("101"),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
0),
isDisplayed()))
        appCompatEditText6.perform(pressImeActionButton())
        
        val chip2 = onView(
allOf(withId(R.id.creditAutoType), withText("Credit Auto"),
childAtPosition(
allOf(withId(R.id.orderType),
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
1)),
0),
isDisplayed()))
        chip2.perform(click())
        
        val chip3 = onView(
allOf(withId(R.id.mountainHouseChip), withText("Mountain House"),
childAtPosition(
allOf(withId(R.id.OrderLocation),
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
5)),
1),
isDisplayed()))
        chip3.perform(click())
        
        val appCompatEditText7 = onView(
allOf(withId(R.id.tip),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
2),
1),
isDisplayed()))
        appCompatEditText7.perform(replaceText("5"), closeSoftKeyboard())
        
        val appCompatEditText8 = onView(
allOf(withId(R.id.tip), withText("5"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
2),
1),
isDisplayed()))
        appCompatEditText8.perform(pressImeActionButton())
        
        val materialButton2 = onView(
allOf(withId(R.id.saveButton), withText("Save"),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
6),
isDisplayed()))
        materialButton2.perform(click())
        
        val floatingActionButton3 = onView(
allOf(withId(R.id.add),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
1),
isDisplayed()))
        floatingActionButton3.perform(click())
        
        val appCompatEditText9 = onView(
allOf(withId(R.id.orderNumber),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
0),
isDisplayed()))
        appCompatEditText9.perform(replaceText("102"), closeSoftKeyboard())
        
        val appCompatEditText10 = onView(
allOf(withId(R.id.orderNumber), withText("102"),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
0),
isDisplayed()))
        appCompatEditText10.perform(pressImeActionButton())
        
        val chip4 = onView(
allOf(withId(R.id.creditManualType), withText("Credit Manual"),
childAtPosition(
allOf(withId(R.id.orderType),
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
1)),
1),
isDisplayed()))
        chip4.perform(click())
        
        val appCompatEditText11 = onView(
allOf(withId(R.id.tip),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
2),
1),
isDisplayed()))
        appCompatEditText11.perform(replaceText("5"), closeSoftKeyboard())
        
        val appCompatEditText12 = onView(
allOf(withId(R.id.tip), withText("5"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
2),
1),
isDisplayed()))
        appCompatEditText12.perform(pressImeActionButton())
        
        val materialButton3 = onView(
allOf(withId(R.id.saveButton), withText("Save"),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
6),
isDisplayed()))
        materialButton3.perform(click())
        
        val floatingActionButton4 = onView(
allOf(withId(R.id.add),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
1),
isDisplayed()))
        floatingActionButton4.perform(click())
        
        val appCompatEditText13 = onView(
allOf(withId(R.id.orderNumber),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
0),
isDisplayed()))
        appCompatEditText13.perform(replaceText("103"), closeSoftKeyboard())
        
        val appCompatEditText14 = onView(
allOf(withId(R.id.orderNumber), withText("103"),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
0),
isDisplayed()))
        appCompatEditText14.perform(pressImeActionButton())
        
        val chip5 = onView(
allOf(withId(R.id.creditManualType), withText("Credit Manual"),
childAtPosition(
allOf(withId(R.id.orderType),
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
1)),
1),
isDisplayed()))
        chip5.perform(click())
        
        val appCompatEditText15 = onView(
allOf(withId(R.id.tip),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
2),
1),
isDisplayed()))
        appCompatEditText15.perform(replaceText("5"), closeSoftKeyboard())
        
        val chip6 = onView(
allOf(withId(R.id.mountainHouseChip), withText("Mountain House"),
childAtPosition(
allOf(withId(R.id.OrderLocation),
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
5)),
1),
isDisplayed()))
        chip6.perform(click())
        
        val appCompatEditText16 = onView(
allOf(withId(R.id.tip), withText("5"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
2),
1),
isDisplayed()))
        appCompatEditText16.perform(pressImeActionButton())
        
        val materialButton4 = onView(
allOf(withId(R.id.saveButton), withText("Save"),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
6),
isDisplayed()))
        materialButton4.perform(click())
        
        val floatingActionButton5 = onView(
allOf(withId(R.id.add),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
1),
isDisplayed()))
        floatingActionButton5.perform(click())
        
        val appCompatEditText17 = onView(
allOf(withId(R.id.orderNumber),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
0),
isDisplayed()))
        appCompatEditText17.perform(replaceText("104"), closeSoftKeyboard())
        
        val appCompatEditText18 = onView(
allOf(withId(R.id.orderNumber), withText("104"),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
0),
isDisplayed()))
        appCompatEditText18.perform(pressImeActionButton())
        
        val chip7 = onView(
allOf(withId(R.id.creditManualType), withText("Credit Manual"),
childAtPosition(
allOf(withId(R.id.orderType),
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
1)),
1),
isDisplayed()))
        chip7.perform(click())
        
        val appCompatEditText19 = onView(
allOf(withId(R.id.tip),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
2),
1),
isDisplayed()))
        appCompatEditText19.perform(replaceText("5"), closeSoftKeyboard())
        
        val materialCheckBox = onView(
allOf(withId(R.id.cashCheckedBox), withText("Cash?"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
2),
2),
isDisplayed()))
        materialCheckBox.perform(click())
        
        val appCompatEditText20 = onView(
allOf(withId(R.id.tip), withText("5"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
2),
1),
isDisplayed()))
        appCompatEditText20.perform(pressImeActionButton())
        
        val materialButton5 = onView(
allOf(withId(R.id.saveButton), withText("Save"),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
6),
isDisplayed()))
        materialButton5.perform(click())
        
        val floatingActionButton6 = onView(
allOf(withId(R.id.add),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
1),
isDisplayed()))
        floatingActionButton6.perform(click())
        
        val appCompatEditText21 = onView(
allOf(withId(R.id.orderNumber),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
0),
isDisplayed()))
        appCompatEditText21.perform(replaceText("105"), closeSoftKeyboard())
        
        val appCompatEditText22 = onView(
allOf(withId(R.id.orderNumber), withText("105"),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
0),
isDisplayed()))
        appCompatEditText22.perform(pressImeActionButton())
        
        val chip8 = onView(
allOf(withId(R.id.creditManualType), withText("Credit Manual"),
childAtPosition(
allOf(withId(R.id.orderType),
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
1)),
1),
isDisplayed()))
        chip8.perform(click())
        
        val appCompatEditText23 = onView(
allOf(withId(R.id.tip),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
2),
1),
isDisplayed()))
        appCompatEditText23.perform(replaceText("5"), closeSoftKeyboard())
        
        val materialCheckBox2 = onView(
allOf(withId(R.id.cashCheckedBox), withText("Cash?"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
2),
2),
isDisplayed()))
        materialCheckBox2.perform(click())
        
        val chip9 = onView(
allOf(withId(R.id.mountainHouseChip), withText("Mountain House"),
childAtPosition(
allOf(withId(R.id.OrderLocation),
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
5)),
1),
isDisplayed()))
        chip9.perform(click())
        
        val appCompatEditText24 = onView(
allOf(withId(R.id.tip), withText("5"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
2),
1),
isDisplayed()))
        appCompatEditText24.perform(pressImeActionButton())
        
        val materialButton6 = onView(
allOf(withId(R.id.saveButton), withText("Save"),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
6),
isDisplayed()))
        materialButton6.perform(click())
        
        val floatingActionButton7 = onView(
allOf(withId(R.id.add),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
1),
isDisplayed()))
        floatingActionButton7.perform(click())
        
        val appCompatEditText25 = onView(
allOf(withId(R.id.orderNumber),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
0),
isDisplayed()))
        appCompatEditText25.perform(replaceText("106"), closeSoftKeyboard())
        
        val appCompatEditText26 = onView(
allOf(withId(R.id.orderNumber), withText("106"),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
0),
isDisplayed()))
        appCompatEditText26.perform(pressImeActionButton())
        
        val chip10 = onView(
allOf(withId(R.id.cashType), withText("Cash"),
childAtPosition(
allOf(withId(R.id.orderType),
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
1)),
2),
isDisplayed()))
        chip10.perform(click())
        
        val appCompatEditText27 = onView(
allOf(withId(R.id.orderTotal),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
3),
1),
isDisplayed()))
        appCompatEditText27.perform(replaceText("50"), closeSoftKeyboard())
        
        val appCompatEditText28 = onView(
allOf(withId(R.id.cashReceived),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
4),
1),
isDisplayed()))
        appCompatEditText28.perform(replaceText("55"), closeSoftKeyboard())
        
        val appCompatEditText29 = onView(
allOf(withId(R.id.cashReceived), withText("55"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
4),
1),
isDisplayed()))
        appCompatEditText29.perform(pressImeActionButton())
        
        val materialButton7 = onView(
allOf(withId(R.id.saveButton), withText("Save"),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
6),
isDisplayed()))
        materialButton7.perform(click())
        
        val floatingActionButton8 = onView(
allOf(withId(R.id.add),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
1),
isDisplayed()))
        floatingActionButton8.perform(click())
        
        val appCompatEditText30 = onView(
allOf(withId(R.id.orderNumber),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
0),
isDisplayed()))
        appCompatEditText30.perform(replaceText("107"), closeSoftKeyboard())
        
        val appCompatEditText31 = onView(
allOf(withId(R.id.orderNumber), withText("107"),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
0),
isDisplayed()))
        appCompatEditText31.perform(pressImeActionButton())
        
        val chip11 = onView(
allOf(withId(R.id.cashType), withText("Cash"),
childAtPosition(
allOf(withId(R.id.orderType),
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
1)),
2),
isDisplayed()))
        chip11.perform(click())
        
        val appCompatEditText32 = onView(
allOf(withId(R.id.orderTotal),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
3),
1),
isDisplayed()))
        appCompatEditText32.perform(replaceText("50"), closeSoftKeyboard())
        
        val appCompatEditText33 = onView(
allOf(withId(R.id.cashReceived),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
4),
1),
isDisplayed()))
        appCompatEditText33.perform(replaceText("55"), closeSoftKeyboard())
        
        val chip12 = onView(
allOf(withId(R.id.mountainHouseChip), withText("Mountain House"),
childAtPosition(
allOf(withId(R.id.OrderLocation),
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
5)),
1),
isDisplayed()))
        chip12.perform(click())
        
        val appCompatEditText34 = onView(
allOf(withId(R.id.cashReceived), withText("55"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
4),
1),
isDisplayed()))
        appCompatEditText34.perform(pressImeActionButton())
        
        val materialButton8 = onView(
allOf(withId(R.id.saveButton), withText("Save"),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
6),
isDisplayed()))
        materialButton8.perform(click())
        
        val floatingActionButton9 = onView(
allOf(withId(R.id.add),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
1),
isDisplayed()))
        floatingActionButton9.perform(click())
        
        val appCompatEditText35 = onView(
allOf(withId(R.id.orderNumber),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
0),
isDisplayed()))
        appCompatEditText35.perform(replaceText("108"), closeSoftKeyboard())
        
        val appCompatEditText36 = onView(
allOf(withId(R.id.orderNumber), withText("108"),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
0),
isDisplayed()))
        appCompatEditText36.perform(pressImeActionButton())
        
        val chip13 = onView(
allOf(withId(R.id.grubhubType), withText("Grubhub"),
childAtPosition(
allOf(withId(R.id.orderType),
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
1)),
3),
isDisplayed()))
        chip13.perform(click())
        
        val appCompatEditText37 = onView(
allOf(withId(R.id.tip),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
2),
1),
isDisplayed()))
        appCompatEditText37.perform(replaceText("5"), closeSoftKeyboard())
        
        val appCompatEditText38 = onView(
allOf(withId(R.id.tip), withText("5"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
2),
1),
isDisplayed()))
        appCompatEditText38.perform(pressImeActionButton())
        
        val materialButton9 = onView(
allOf(withId(R.id.saveButton), withText("Save"),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
6),
isDisplayed()))
        materialButton9.perform(click())
        
        val floatingActionButton10 = onView(
allOf(withId(R.id.add),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
1),
isDisplayed()))
        floatingActionButton10.perform(click())
        
        val appCompatEditText39 = onView(
allOf(withId(R.id.orderNumber),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
0),
isDisplayed()))
        appCompatEditText39.perform(replaceText("109"), closeSoftKeyboard())
        
        val appCompatEditText40 = onView(
allOf(withId(R.id.orderNumber), withText("109"),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
0),
isDisplayed()))
        appCompatEditText40.perform(pressImeActionButton())
        
        val chip14 = onView(
allOf(withId(R.id.grubhubType), withText("Grubhub"),
childAtPosition(
allOf(withId(R.id.orderType),
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
1)),
3),
isDisplayed()))
        chip14.perform(click())
        
        val chip15 = onView(
allOf(withId(R.id.mountainHouseChip), withText("Mountain House"),
childAtPosition(
allOf(withId(R.id.OrderLocation),
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
5)),
1),
isDisplayed()))
        chip15.perform(click())
        
        val appCompatEditText41 = onView(
allOf(withId(R.id.tip),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
2),
1),
isDisplayed()))
        appCompatEditText41.perform(replaceText("5"), closeSoftKeyboard())
        
        val appCompatEditText42 = onView(
allOf(withId(R.id.tip), withText("5"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
2),
1),
isDisplayed()))
        appCompatEditText42.perform(pressImeActionButton())
        
        val materialButton10 = onView(
allOf(withId(R.id.saveButton), withText("Save"),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
6),
isDisplayed()))
        materialButton10.perform(click())
        
        val floatingActionButton11 = onView(
allOf(withId(R.id.add),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
1),
isDisplayed()))
        floatingActionButton11.perform(click())
        
        val appCompatEditText43 = onView(
allOf(withId(R.id.orderNumber),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
0),
isDisplayed()))
        appCompatEditText43.perform(replaceText("110"), closeSoftKeyboard())
        
        val appCompatEditText44 = onView(
allOf(withId(R.id.orderNumber), withText("110"),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
0),
isDisplayed()))
        appCompatEditText44.perform(pressImeActionButton())
        
        val chip16 = onView(
allOf(withId(R.id.grubhubType), withText("Grubhub"),
childAtPosition(
allOf(withId(R.id.orderType),
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
1)),
3),
isDisplayed()))
        chip16.perform(click())
        
        val appCompatEditText45 = onView(
allOf(withId(R.id.tip),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
2),
1),
isDisplayed()))
        appCompatEditText45.perform(replaceText("5"), closeSoftKeyboard())
        
        val materialCheckBox3 = onView(
allOf(withId(R.id.cashCheckedBox), withText("Cash?"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
2),
2),
isDisplayed()))
        materialCheckBox3.perform(click())
        
        val appCompatEditText46 = onView(
allOf(withId(R.id.tip), withText("5"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
2),
1),
isDisplayed()))
        appCompatEditText46.perform(pressImeActionButton())
        
        val materialButton11 = onView(
allOf(withId(R.id.saveButton), withText("Save"),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
6),
isDisplayed()))
        materialButton11.perform(click())
        
        val floatingActionButton12 = onView(
allOf(withId(R.id.add),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
1),
isDisplayed()))
        floatingActionButton12.perform(click())
        
        val appCompatEditText47 = onView(
allOf(withId(R.id.orderNumber),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
0),
isDisplayed()))
        appCompatEditText47.perform(replaceText("111"), closeSoftKeyboard())
        
        val appCompatEditText48 = onView(
allOf(withId(R.id.orderNumber), withText("111"),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
0),
isDisplayed()))
        appCompatEditText48.perform(pressImeActionButton())
        
        val chip17 = onView(
allOf(withId(R.id.grubhubType), withText("Grubhub"),
childAtPosition(
allOf(withId(R.id.orderType),
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
1)),
3),
isDisplayed()))
        chip17.perform(click())
        
        val appCompatEditText49 = onView(
allOf(withId(R.id.tip),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
2),
1),
isDisplayed()))
        appCompatEditText49.perform(replaceText("5"), closeSoftKeyboard())
        
        val materialCheckBox4 = onView(
allOf(withId(R.id.cashCheckedBox), withText("Cash?"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
2),
2),
isDisplayed()))
        materialCheckBox4.perform(click())
        
        val chip18 = onView(
allOf(withId(R.id.mountainHouseChip), withText("Mountain House"),
childAtPosition(
allOf(withId(R.id.OrderLocation),
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
5)),
1),
isDisplayed()))
        chip18.perform(click())
        
        val appCompatEditText50 = onView(
allOf(withId(R.id.tip), withText("5"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
2),
1),
isDisplayed()))
        appCompatEditText50.perform(pressImeActionButton())
        
        val materialButton12 = onView(
allOf(withId(R.id.saveButton), withText("Save"),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
6),
isDisplayed()))
        materialButton12.perform(click())
        
        val floatingActionButton13 = onView(
allOf(withId(R.id.add),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
1),
isDisplayed()))
        floatingActionButton13.perform(click())
        
        val appCompatEditText51 = onView(
allOf(withId(R.id.orderNumber),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
0),
isDisplayed()))
        appCompatEditText51.perform(replaceText("112"), closeSoftKeyboard())
        
        val appCompatEditText52 = onView(
allOf(withId(R.id.orderNumber), withText("112"),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
0),
isDisplayed()))
        appCompatEditText52.perform(pressImeActionButton())
        
        val chip19 = onView(
allOf(withId(R.id.levelUpType), withText("LevelUp"),
childAtPosition(
allOf(withId(R.id.orderType),
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
1)),
4),
isDisplayed()))
        chip19.perform(click())
        
        val appCompatEditText53 = onView(
allOf(withId(R.id.tip),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
2),
1),
isDisplayed()))
        appCompatEditText53.perform(replaceText("5"), closeSoftKeyboard())
        
        val appCompatEditText54 = onView(
allOf(withId(R.id.tip), withText("5"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
2),
1),
isDisplayed()))
        appCompatEditText54.perform(pressImeActionButton())
        
        val materialButton13 = onView(
allOf(withId(R.id.saveButton), withText("Save"),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
6),
isDisplayed()))
        materialButton13.perform(click())
        
        val floatingActionButton14 = onView(
allOf(withId(R.id.add),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
1),
isDisplayed()))
        floatingActionButton14.perform(click())
        
        val appCompatEditText55 = onView(
allOf(withId(R.id.orderNumber),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
0),
isDisplayed()))
        appCompatEditText55.perform(replaceText("113"), closeSoftKeyboard())
        
        val appCompatEditText56 = onView(
allOf(withId(R.id.orderNumber), withText("113"),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
0),
isDisplayed()))
        appCompatEditText56.perform(pressImeActionButton())
        
        val chip20 = onView(
allOf(withId(R.id.levelUpType), withText("LevelUp"),
childAtPosition(
allOf(withId(R.id.orderType),
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
1)),
4),
isDisplayed()))
        chip20.perform(click())
        
        val appCompatEditText57 = onView(
allOf(withId(R.id.tip),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
2),
1),
isDisplayed()))
        appCompatEditText57.perform(replaceText("5"), closeSoftKeyboard())
        
        val chip21 = onView(
allOf(withId(R.id.mountainHouseChip), withText("Mountain House"),
childAtPosition(
allOf(withId(R.id.OrderLocation),
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
5)),
1),
isDisplayed()))
        chip21.perform(click())
        
        val appCompatEditText58 = onView(
allOf(withId(R.id.tip), withText("5"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
2),
1),
isDisplayed()))
        appCompatEditText58.perform(pressImeActionButton())
        
        val materialButton14 = onView(
allOf(withId(R.id.saveButton), withText("Save"),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
6),
isDisplayed()))
        materialButton14.perform(click())
        
        val floatingActionButton15 = onView(
allOf(withId(R.id.add),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
1),
isDisplayed()))
        floatingActionButton15.perform(click())
        
        val appCompatEditText59 = onView(
allOf(withId(R.id.orderNumber),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
0),
isDisplayed()))
        appCompatEditText59.perform(replaceText("114"), closeSoftKeyboard())
        
        val appCompatEditText60 = onView(
allOf(withId(R.id.orderNumber), withText("114"),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
0),
isDisplayed()))
        appCompatEditText60.perform(pressImeActionButton())
        
        val chip22 = onView(
allOf(withId(R.id.levelUpType), withText("LevelUp"),
childAtPosition(
allOf(withId(R.id.orderType),
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
1)),
4),
isDisplayed()))
        chip22.perform(click())
        
        val appCompatEditText61 = onView(
allOf(withId(R.id.tip),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
2),
1),
isDisplayed()))
        appCompatEditText61.perform(replaceText("5"), closeSoftKeyboard())
        
        val materialCheckBox5 = onView(
allOf(withId(R.id.cashCheckedBox), withText("Cash?"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
2),
2),
isDisplayed()))
        materialCheckBox5.perform(click())
        
        val appCompatEditText62 = onView(
allOf(withId(R.id.tip), withText("5"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
2),
1),
isDisplayed()))
        appCompatEditText62.perform(pressImeActionButton())
        
        val materialButton15 = onView(
allOf(withId(R.id.saveButton), withText("Save"),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
6),
isDisplayed()))
        materialButton15.perform(click())
        
        val floatingActionButton16 = onView(
allOf(withId(R.id.add),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
1),
isDisplayed()))
        floatingActionButton16.perform(click())
        
        val appCompatEditText63 = onView(
allOf(withId(R.id.orderNumber),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
0),
isDisplayed()))
        appCompatEditText63.perform(replaceText("115"), closeSoftKeyboard())
        
        val appCompatEditText64 = onView(
allOf(withId(R.id.orderNumber), withText("115"),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
0),
isDisplayed()))
        appCompatEditText64.perform(pressImeActionButton())
        
        val chip23 = onView(
allOf(withId(R.id.levelUpType), withText("LevelUp"),
childAtPosition(
allOf(withId(R.id.orderType),
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
1)),
4),
isDisplayed()))
        chip23.perform(click())
        
        val appCompatEditText65 = onView(
allOf(withId(R.id.tip),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
2),
1),
isDisplayed()))
        appCompatEditText65.perform(replaceText("5"), closeSoftKeyboard())
        
        val materialCheckBox6 = onView(
allOf(withId(R.id.cashCheckedBox), withText("Cash?"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
2),
2),
isDisplayed()))
        materialCheckBox6.perform(click())
        
        val chip24 = onView(
allOf(withId(R.id.mountainHouseChip), withText("Mountain House"),
childAtPosition(
allOf(withId(R.id.OrderLocation),
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
5)),
1),
isDisplayed()))
        chip24.perform(click())
        
        val appCompatEditText66 = onView(
allOf(withId(R.id.tip), withText("5"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
2),
1),
isDisplayed()))
        appCompatEditText66.perform(pressImeActionButton())
        
        val materialButton16 = onView(
allOf(withId(R.id.saveButton), withText("Save"),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
6),
isDisplayed()))
        materialButton16.perform(click())
        
        val floatingActionButton17 = onView(
allOf(withId(R.id.add),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
1),
isDisplayed()))
        floatingActionButton17.perform(click())
        
        val appCompatEditText67 = onView(
allOf(withId(R.id.orderNumber),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
0),
isDisplayed()))
        appCompatEditText67.perform(replaceText("116"), closeSoftKeyboard())
        
        val appCompatEditText68 = onView(
allOf(withId(R.id.orderNumber), withText("116"),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
0),
isDisplayed()))
        appCompatEditText68.perform(pressImeActionButton())
        
        val chip25 = onView(
allOf(withId(R.id.otherType), withText("Other"),
childAtPosition(
allOf(withId(R.id.orderType),
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
1)),
5),
isDisplayed()))
        chip25.perform(click())
        
        val appCompatEditText69 = onView(
allOf(withId(R.id.tip),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
2),
1),
isDisplayed()))
        appCompatEditText69.perform(replaceText("5"), closeSoftKeyboard())
        
        val materialCheckBox7 = onView(
allOf(withId(R.id.cashCheckedBox), withText("Cash?"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
2),
2),
isDisplayed()))
        materialCheckBox7.perform(click())
        
        val materialCheckBox8 = onView(
allOf(withId(R.id.cashCheckedBox), withText("Cash?"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
2),
2),
isDisplayed()))
        materialCheckBox8.perform(click())
        
        val materialCheckBox9 = onView(
allOf(withId(R.id.cashCheckedBox), withText("Cash?"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
2),
2),
isDisplayed()))
        materialCheckBox9.perform(click())
        
        val materialCheckBox10 = onView(
allOf(withId(R.id.cashCheckedBox), withText("Cash?"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
2),
2),
isDisplayed()))
        materialCheckBox10.perform(click())
        
        val appCompatEditText70 = onView(
allOf(withId(R.id.tip), withText("5"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
2),
1),
isDisplayed()))
        appCompatEditText70.perform(pressImeActionButton())
        
        val appCompatEditText71 = onView(
allOf(withId(R.id.orderTotal),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
3),
1),
isDisplayed()))
        appCompatEditText71.perform(pressImeActionButton())
        
        val appCompatEditText72 = onView(
allOf(withId(R.id.cashReceived),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
4),
1),
isDisplayed()))
        appCompatEditText72.perform(pressImeActionButton())
        
        val materialButton17 = onView(
allOf(withId(R.id.saveButton), withText("Save"),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
6),
isDisplayed()))
        materialButton17.perform(click())
        
        val floatingActionButton18 = onView(
allOf(withId(R.id.add),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
1),
isDisplayed()))
        floatingActionButton18.perform(click())
        
        val appCompatEditText73 = onView(
allOf(withId(R.id.orderNumber),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
0),
isDisplayed()))
        appCompatEditText73.perform(replaceText("117"), closeSoftKeyboard())
        
        val appCompatEditText74 = onView(
allOf(withId(R.id.orderNumber), withText("117"),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
0),
isDisplayed()))
        appCompatEditText74.perform(pressImeActionButton())
        
        val chip26 = onView(
allOf(withId(R.id.otherType), withText("Other"),
childAtPosition(
allOf(withId(R.id.orderType),
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
1)),
5),
isDisplayed()))
        chip26.perform(click())
        
        val appCompatEditText75 = onView(
allOf(withId(R.id.tip),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
2),
1),
isDisplayed()))
        appCompatEditText75.perform(replaceText("5"), closeSoftKeyboard())
        
        val chip27 = onView(
allOf(withId(R.id.mountainHouseChip), withText("Mountain House"),
childAtPosition(
allOf(withId(R.id.OrderLocation),
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
5)),
1),
isDisplayed()))
        chip27.perform(click())
        
        val appCompatEditText76 = onView(
allOf(withId(R.id.tip), withText("5"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
2),
1),
isDisplayed()))
        appCompatEditText76.perform(pressImeActionButton())
        
        val appCompatEditText77 = onView(
allOf(withId(R.id.orderTotal),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
3),
1),
isDisplayed()))
        appCompatEditText77.perform(pressImeActionButton())
        
        val appCompatEditText78 = onView(
allOf(withId(R.id.cashReceived),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
4),
1),
isDisplayed()))
        appCompatEditText78.perform(pressImeActionButton())
        
        val materialButton18 = onView(
allOf(withId(R.id.saveButton), withText("Save"),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
6),
isDisplayed()))
        materialButton18.perform(click())
        
        val floatingActionButton19 = onView(
allOf(withId(R.id.add),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
1),
isDisplayed()))
        floatingActionButton19.perform(click())
        
        val appCompatEditText79 = onView(
allOf(withId(R.id.orderNumber),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
0),
isDisplayed()))
        appCompatEditText79.perform(replaceText("117"), closeSoftKeyboard())
        
        val appCompatEditText80 = onView(
allOf(withId(R.id.orderNumber), withText("117"),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
0),
isDisplayed()))
        appCompatEditText80.perform(pressImeActionButton())
        
        val materialButton19 = onView(
allOf(withId(R.id.saveButton), withText("Save"),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
6),
isDisplayed()))
        materialButton19.perform(click())
        
        val floatingActionButton20 = onView(
allOf(withId(R.id.add),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
1),
isDisplayed()))
        floatingActionButton20.perform(click())
        
        val appCompatEditText81 = onView(
allOf(withId(R.id.orderNumber),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
0),
isDisplayed()))
        appCompatEditText81.perform(replaceText("118"), closeSoftKeyboard())
        
        val appCompatEditText82 = onView(
allOf(withId(R.id.orderNumber), withText("118"),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
0),
isDisplayed()))
        appCompatEditText82.perform(pressImeActionButton())
        
        val chip28 = onView(
allOf(withId(R.id.otherType), withText("Other"),
childAtPosition(
allOf(withId(R.id.orderType),
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
1)),
5),
isDisplayed()))
        chip28.perform(click())
        
        val appCompatEditText83 = onView(
allOf(withId(R.id.tip),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
2),
1),
isDisplayed()))
        appCompatEditText83.perform(replaceText("5"), closeSoftKeyboard())
        
        val materialCheckBox11 = onView(
allOf(withId(R.id.cashCheckedBox), withText("Cash?"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
2),
2),
isDisplayed()))
        materialCheckBox11.perform(click())
        
        val appCompatEditText84 = onView(
allOf(withId(R.id.tip), withText("5"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
2),
1),
isDisplayed()))
        appCompatEditText84.perform(pressImeActionButton())
        
        val appCompatEditText85 = onView(
allOf(withId(R.id.orderTotal),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
3),
1),
isDisplayed()))
        appCompatEditText85.perform(pressImeActionButton())
        
        val appCompatEditText86 = onView(
allOf(withId(R.id.cashReceived),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
4),
1),
isDisplayed()))
        appCompatEditText86.perform(pressImeActionButton())
        
        val materialButton20 = onView(
allOf(withId(R.id.saveButton), withText("Save"),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
6),
isDisplayed()))
        materialButton20.perform(click())
        
        val floatingActionButton21 = onView(
allOf(withId(R.id.add),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
1),
isDisplayed()))
        floatingActionButton21.perform(click())
        
        val appCompatEditText87 = onView(
allOf(withId(R.id.orderNumber),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
0),
isDisplayed()))
        appCompatEditText87.perform(replaceText("118"), closeSoftKeyboard())
        
        val appCompatEditText88 = onView(
allOf(withId(R.id.orderNumber), withText("118"),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
0),
isDisplayed()))
        appCompatEditText88.perform(pressImeActionButton())
        
        val materialButton21 = onView(
allOf(withId(R.id.saveButton), withText("Save"),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
6),
isDisplayed()))
        materialButton21.perform(click())
        
        val floatingActionButton22 = onView(
allOf(withId(R.id.add),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
1),
isDisplayed()))
        floatingActionButton22.perform(click())
        
        val appCompatEditText89 = onView(
allOf(withId(R.id.orderNumber),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
0),
isDisplayed()))
        appCompatEditText89.perform(replaceText("119"), closeSoftKeyboard())
        
        val appCompatEditText90 = onView(
allOf(withId(R.id.orderNumber), withText("119"),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
0),
isDisplayed()))
        appCompatEditText90.perform(pressImeActionButton())
        
        val chip29 = onView(
allOf(withId(R.id.otherType), withText("Other"),
childAtPosition(
allOf(withId(R.id.orderType),
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
1)),
5),
isDisplayed()))
        chip29.perform(click())
        
        val appCompatEditText91 = onView(
allOf(withId(R.id.tip),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
2),
1),
isDisplayed()))
        appCompatEditText91.perform(replaceText("5"), closeSoftKeyboard())
        
        val materialCheckBox12 = onView(
allOf(withId(R.id.cashCheckedBox), withText("Cash?"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
2),
2),
isDisplayed()))
        materialCheckBox12.perform(click())
        
        val chip30 = onView(
allOf(withId(R.id.mountainHouseChip), withText("Mountain House"),
childAtPosition(
allOf(withId(R.id.OrderLocation),
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
5)),
1),
isDisplayed()))
        chip30.perform(click())
        
        val appCompatEditText92 = onView(
allOf(withId(R.id.tip), withText("5"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
2),
1),
isDisplayed()))
        appCompatEditText92.perform(pressImeActionButton())
        
        val appCompatEditText93 = onView(
allOf(withId(R.id.orderTotal),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
3),
1),
isDisplayed()))
        appCompatEditText93.perform(pressImeActionButton())
        
        val appCompatEditText94 = onView(
allOf(withId(R.id.cashReceived),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
4),
1),
isDisplayed()))
        appCompatEditText94.perform(pressImeActionButton())
        
        val materialButton22 = onView(
allOf(withId(R.id.saveButton), withText("Save"),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
6),
isDisplayed()))
        materialButton22.perform(click())
        
        val floatingActionButton23 = onView(
allOf(withId(R.id.add),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
1),
isDisplayed()))
        floatingActionButton23.perform(click())
        
        val appCompatEditText95 = onView(
allOf(withId(R.id.orderNumber),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
0),
isDisplayed()))
        appCompatEditText95.perform(replaceText("120"), closeSoftKeyboard())
        
        val appCompatEditText96 = onView(
allOf(withId(R.id.orderNumber), withText("120"),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
0),
isDisplayed()))
        appCompatEditText96.perform(pressImeActionButton())
        
        val chip31 = onView(
allOf(withId(R.id.otherType), withText("Other"),
childAtPosition(
allOf(withId(R.id.orderType),
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
1)),
5),
isDisplayed()))
        chip31.perform(click())
        
        val appCompatEditText97 = onView(
allOf(withId(R.id.orderTotal),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
3),
1),
isDisplayed()))
        appCompatEditText97.perform(replaceText("50"), closeSoftKeyboard())
        
        val appCompatEditText98 = onView(
allOf(withId(R.id.cashReceived),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
4),
1),
isDisplayed()))
        appCompatEditText98.perform(replaceText("55"), closeSoftKeyboard())
        
        val appCompatEditText99 = onView(
allOf(withId(R.id.tip),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
2),
1),
isDisplayed()))
        appCompatEditText99.perform(replaceText("5"), closeSoftKeyboard())
        
        val appCompatEditText100 = onView(
allOf(withId(R.id.orderTotal), withText("50"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
3),
1),
isDisplayed()))
        appCompatEditText100.perform(replaceText("50"))
        
        val appCompatEditText101 = onView(
allOf(withId(R.id.orderTotal), withText("50"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
3),
1),
isDisplayed()))
        appCompatEditText101.perform(closeSoftKeyboard())
        
        val materialCheckBox13 = onView(
allOf(withId(R.id.cashCheckedBox), withText("Cash?"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
2),
2),
isDisplayed()))
        materialCheckBox13.perform(click())
        
        val appCompatEditText102 = onView(
allOf(withId(R.id.orderTotal), withText("50"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
3),
1),
isDisplayed()))
        appCompatEditText102.perform(pressImeActionButton())
        
        val appCompatEditText103 = onView(
allOf(withId(R.id.cashReceived), withText("55"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
4),
1),
isDisplayed()))
        appCompatEditText103.perform(pressImeActionButton())
        
        val materialButton23 = onView(
allOf(withId(R.id.saveButton), withText("Save"),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
6),
isDisplayed()))
        materialButton23.perform(click())
        
        val floatingActionButton24 = onView(
allOf(withId(R.id.add),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
1),
isDisplayed()))
        floatingActionButton24.perform(click())
        
        val appCompatEditText104 = onView(
allOf(withId(R.id.orderNumber),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
0),
isDisplayed()))
        appCompatEditText104.perform(replaceText("121"), closeSoftKeyboard())
        
        val appCompatEditText105 = onView(
allOf(withId(R.id.orderNumber), withText("121"),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
0),
isDisplayed()))
        appCompatEditText105.perform(pressImeActionButton())
        
        val chip32 = onView(
allOf(withId(R.id.otherType), withText("Other"),
childAtPosition(
allOf(withId(R.id.orderType),
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
1)),
5),
isDisplayed()))
        chip32.perform(click())
        
        val appCompatEditText106 = onView(
allOf(withId(R.id.orderTotal),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
3),
1),
isDisplayed()))
        appCompatEditText106.perform(replaceText("50"), closeSoftKeyboard())
        
        val appCompatEditText107 = onView(
allOf(withId(R.id.cashReceived),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
4),
1),
isDisplayed()))
        appCompatEditText107.perform(replaceText("55"), closeSoftKeyboard())
        
        val appCompatEditText108 = onView(
allOf(withId(R.id.tip),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
2),
1),
isDisplayed()))
        appCompatEditText108.perform(replaceText("5"), closeSoftKeyboard())
        
        val materialCheckBox14 = onView(
allOf(withId(R.id.cashCheckedBox), withText("Cash?"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
2),
2),
isDisplayed()))
        materialCheckBox14.perform(click())
        
        val chip33 = onView(
allOf(withId(R.id.mountainHouseChip), withText("Mountain House"),
childAtPosition(
allOf(withId(R.id.OrderLocation),
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
5)),
1),
isDisplayed()))
        chip33.perform(click())
        
        val appCompatEditText109 = onView(
allOf(withId(R.id.tip), withText("5"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
2),
1),
isDisplayed()))
        appCompatEditText109.perform(pressImeActionButton())
        
        val appCompatEditText110 = onView(
allOf(withId(R.id.orderTotal), withText("50"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
3),
1),
isDisplayed()))
        appCompatEditText110.perform(pressImeActionButton())
        
        val appCompatEditText111 = onView(
allOf(withId(R.id.cashReceived), withText("55"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
4),
1),
isDisplayed()))
        appCompatEditText111.perform(pressImeActionButton())
        
        val materialButton24 = onView(
allOf(withId(R.id.saveButton), withText("Save"),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
6),
isDisplayed()))
        materialButton24.perform(click())
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
