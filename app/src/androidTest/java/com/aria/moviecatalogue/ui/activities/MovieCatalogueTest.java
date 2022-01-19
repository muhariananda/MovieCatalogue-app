package com.aria.moviecatalogue.ui.activities;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.rule.ActivityTestRule;

import com.aria.moviecatalogue.R;
import com.aria.moviecatalogue.utils.EspressoIdlingResource;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

public class MovieCatalogueTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getEspressoIdlingResource());
    }

    @After
    public void tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getEspressoIdlingResource());
    }

    @Test
    public void toDetailActivityTest() {
        onView(withId(R.id.rv_movies)).check(matches(isDisplayed()));
        onView(withId(R.id.rv_movies)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.tv_detail_title)).check(matches(isDisplayed()));

        //data berubah - ubah
//        onView(withId(R.id.tv_detail_title)).check(matches(withText("Guardians of the Galaxy Vol. 2")));
    }

}