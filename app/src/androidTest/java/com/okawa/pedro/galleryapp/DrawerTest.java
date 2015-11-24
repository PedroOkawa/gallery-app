package com.okawa.pedro.galleryapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.okawa.pedro.galleryapp.ui.main.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import greendao.ImageData;

import static android.os.SystemClock.sleep;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.contrib.DrawerActions.openDrawer;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by pokawa on 24/11/15.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class DrawerTest {

    private static final int INITIAL_DELAY = 2000;
    private static final int INTERACTION_DELAY = 1000;

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setup() {
        sleep(INITIAL_DELAY);
    }

    @Test
    public void openAllImagesOptionAndScrollItem() {
        openDrawer(R.id.drawerLayout);
        sleep(INTERACTION_DELAY);
        onView(withText(ImageData.TYPE_ALL)).perform(click());

        onView(allOf(withId(R.id.rvActivityMainImages), isDisplayed()))
                .perform(RecyclerViewActions.scrollToPosition(0),
                        RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(isRoot()).perform(OrientationChangeAction.orientationLandscape());

        sleep(INTERACTION_DELAY);

        onView(withId(R.id.svActivityDetails)).perform(swipeUp());
    }

    @Test
    public void openPhotosOptionAndScrollItem() {
        openDrawer(R.id.drawerLayout);
        sleep(INTERACTION_DELAY);
        onView(withText(ImageData.TYPE_PHOTO)).perform(click());

        onView(allOf(withId(R.id.rvActivityMainImages), isDisplayed()))
                .perform(RecyclerViewActions.scrollToPosition(0),
                        RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(isRoot()).perform(OrientationChangeAction.orientationLandscape());

        sleep(INTERACTION_DELAY);

        onView(withId(R.id.svActivityDetails)).perform(swipeUp());
    }

    @Test
    public void openIllustrationsOptionAndScrollItem() {
        openDrawer(R.id.drawerLayout);
        sleep(INTERACTION_DELAY);
        onView(withText(ImageData.TYPE_ILLUSTRATION)).perform(click());

        onView(allOf(withId(R.id.rvActivityMainImages), isDisplayed()))
                .perform(RecyclerViewActions.scrollToPosition(0),
                        RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(isRoot()).perform(OrientationChangeAction.orientationLandscape());

        sleep(INTERACTION_DELAY);

        onView(withId(R.id.svActivityDetails)).perform(swipeUp());
    }

    @Test
    public void openVectorsOptionAndScrollItem() {
        openDrawer(R.id.drawerLayout);
        sleep(INTERACTION_DELAY);
        onView(withText(ImageData.TYPE_VECTOR)).perform(click());

        onView(allOf(withId(R.id.rvActivityMainImages), isDisplayed()))
                .perform(RecyclerViewActions.scrollToPosition(0),
                        RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(isRoot()).perform(OrientationChangeAction.orientationLandscape());

        sleep(INTERACTION_DELAY);

        onView(withId(R.id.svActivityDetails)).perform(swipeUp());
    }

    @Test
    public void openGithubLink() {
        openDrawer(R.id.drawerLayout);
        sleep(INTERACTION_DELAY);
        onView(withId(R.id.footerNavigationView)).perform(click());
    }
}
