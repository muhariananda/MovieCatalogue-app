package com.aria.moviecatalogue.ui.fragments;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.rule.ActivityTestRule;

import com.aria.moviecatalogue.R;
import com.aria.moviecatalogue.testing.SingleFragmentActivity;
import com.aria.moviecatalogue.utils.EspressoIdlingResource;
import com.aria.moviecatalogue.utils.RecyclerViewItemCountAssertion;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class TvShowFragmentTest {

    @Rule
    public ActivityTestRule<SingleFragmentActivity> activityTestRule = new ActivityTestRule<>(SingleFragmentActivity.class);
    private TvShowFragment tvShowFragment = new TvShowFragment();

    @Before
    public void setup() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getEspressoIdlingResource());
        activityTestRule.getActivity().setFragment(tvShowFragment);
    }

    @After
    public void tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getEspressoIdlingResource());
    }

    @Test
    public void loadTvShows() {
        onView(withId(R.id.rv_tvShow)).check(matches(isDisplayed()));
        onView(withId(R.id.rv_tvShow)).check(new RecyclerViewItemCountAssertion(20));
    }

}