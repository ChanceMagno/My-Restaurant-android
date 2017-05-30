package com.example.guest.myrestaurants;

import android.support.test.rule.ActivityTestRule;

import com.example.guest.myrestaurants.ui.MainActivity;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


public class MainActivityInstrumentationTest {
    @Rule
    public ActivityTestRule<MainActivity> ActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void validateEditText(){
        onView(withId(R.id.locationEditText)).perform(typeText("12345"))
                .check(matches(withText("12345")));
    }

    @Test
    public void locationIsSentToRestaurantsActivity() {
        String location = "54321";
        onView(withId(R.id.locationEditText)).perform(typeText(location));
        onView(withId(R.id.findRestaurantButton)).perform(click());
        onView(withId(R.id.locationTextView)).check(matches
                (withText("Restaurants near " + location)));
    }

}
