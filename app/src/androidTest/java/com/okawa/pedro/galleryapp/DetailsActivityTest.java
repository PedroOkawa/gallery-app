package com.okawa.pedro.galleryapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.test.suitebuilder.annotation.LargeTest;

import com.okawa.pedro.galleryapp.ui.main.MainActivity;

import org.junit.Rule;
import org.junit.Test;

import static android.os.SystemClock.sleep;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by pokawa on 24/11/15.
 */
@LargeTest
public class DetailsActivityTest {

    private static final int INITIAL_DELAY = 2000;
    private static final int TRANSITION_DELAY = 1000;

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void setup() {
        sleep(INITIAL_DELAY);
    }

    @Test
    public void testShare() {
        onView(allOf(withId(R.id.rvActivityMainImages), isDisplayed()))
                .perform(RecyclerViewActions.scrollToPosition(0),
                        RecyclerViewActions.actionOnItemAtPosition(0, click()));

        sleep(TRANSITION_DELAY);

        onView(withId(R.id.action_share)).perform(click());
    }

}
