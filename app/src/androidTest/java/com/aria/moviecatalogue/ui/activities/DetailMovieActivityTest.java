package com.aria.moviecatalogue.ui.activities;


import android.content.Context;
import android.content.Intent;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.aria.moviecatalogue.R;
import com.aria.moviecatalogue.data.source.local.entity.MovieEntity;
import com.aria.moviecatalogue.utils.EspressoIdlingResource;
import com.aria.moviecatalogue.utils.FakeDataDummy;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class DetailMovieActivityTest {

    private MovieEntity dummyMovies = FakeDataDummy.generateDummyMovies().get(0);

    @Rule
    public ActivityTestRule<DetailMovieActivity> activityTestRule = new ActivityTestRule<DetailMovieActivity>(DetailMovieActivity.class) {
        @Override
        protected Intent getActivityIntent() {
            Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
            Intent result = new Intent(targetContext, DetailMovieActivity.class);
            result.putExtra(DetailMovieActivity.EXTRA_CONTENT, dummyMovies.getId());

            return result;
        }
    };

    @Before
    public void setup() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getEspressoIdlingResource());
    }

    @After
    public void tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getEspressoIdlingResource());
    }

    @Test
    public void loadMovies() {
        onView(withId(R.id.tv_detail_title)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_detail_title)).check(matches(withText(dummyMovies.getTitle())));
        onView(withId(R.id.tv_detail_release)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_detail_release)).check(matches(withText(dummyMovies.getReleaseDate().split("-")[0])));
        onView(withId(R.id.tv_detail_overview)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_detail_overview)).check(matches(withText(dummyMovies.getOverview())));
    }
}