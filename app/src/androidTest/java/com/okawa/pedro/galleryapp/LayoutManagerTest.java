package com.okawa.pedro.galleryapp;

import android.support.test.rule.ActivityTestRule;
import android.test.suitebuilder.annotation.LargeTest;

import com.okawa.pedro.galleryapp.ui.main.MainActivity;

import org.junit.Rule;
import org.junit.Test;

import static android.os.SystemClock.sleep;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;

/**
 * Created by pokawa on 24/11/15.
 */
@LargeTest
public class LayoutManagerTest {

    private static final int INITIAL_DELAY = 2000;
    private static final int INTERACTION_DELAY = 2000;

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void setup() {
        sleep(INITIAL_DELAY);
    }

    @Test
    public void changeColumnsNumber() {
        onView(isRoot()).perform(OrientationChangeAction.orientationLandscape());
        sleep(INTERACTION_DELAY);
        onView(isRoot()).perform(OrientationChangeAction.orientationPortrait());
        sleep(INTERACTION_DELAY);
    }
}
