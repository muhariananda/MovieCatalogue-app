package com.aria.moviecatalogue.ui.activities;

import android.content.Context;
import android.content.Intent;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.aria.moviecatalogue.R;
import com.aria.moviecatalogue.data.source.local.entity.TvShowEntity;
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

public class DetailTvShowActivityTest {

    private TvShowEntity dummyTvShow = FakeDataDummy.generateDummyTvShows().get(0);

    @Rule
    public ActivityTestRule<DetailTvShowActivity> activityRule = new ActivityTestRule<DetailTvShowActivity>(DetailTvShowActivity.class) {
        @Override
        protected Intent getActivityIntent() {
            Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
            Intent result = new Intent(targetContext, DetailTvShowActivity.class);
            result.putExtra(DetailTvShowActivity.EXTRA_CONTENT, dummyTvShow.getId());

            return result;
        }
    };

    @Before
    public void setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getEspressoIdlingResource());
    }

    @After
    public void tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getEspressoIdlingResource());
    }

    @Test
    public void loadTvShows() {
        onView(withId(R.id.tv_detail_title)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_detail_title)).check(matches(withText(dummyTvShow.getName())));
        onView(withId(R.id.tv_detail_release)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_detail_release)).check(matches(withText(dummyTvShow.getFirstAirDate().split("-")[0])));
        onView(withId(R.id.tv_detail_overview)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_detail_overview)).check(matches(withText(dummyTvShow.getOverview())));
    }
}