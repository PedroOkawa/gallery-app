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

import static android.os.SystemClock.sleep;
import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.contrib.DrawerActions.openDrawer;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by pokawa on 24/11/15.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class GalleryAppTests {

    private static final int INDEX_ALL = 0;
    private static final int INDEX_PHOTO = 1;
    private static final int INDEX_ILLUSTRATION = 2;
    private static final int INDEX_VECTOR = 3;

    private static final int TOTAL_OBJECTS_OPEN = 5;

    private static final int INITIAL_DELAY = 1000;
    private static final int INTERACTION_DELAY = 500;

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setup() {
        closeSoftKeyboard();
        sleep(INITIAL_DELAY);
    }

    @Test
    public void changeColumnsNumber() {
        onView(isRoot()).perform(OrientationChangeAction.orientationLandscape());
        sleep(INTERACTION_DELAY);
        onView(isRoot()).perform(OrientationChangeAction.orientationPortrait());
        sleep(INTERACTION_DELAY);
    }

    @Test
    public void openAllImagesOptionAndScrollItem() {
        openDrawer(R.id.drawerLayout);
        sleep(INTERACTION_DELAY);

        onView(allOf(withId(R.id.rvNavigationView), isDisplayed()))
                .perform(RecyclerViewActions.scrollToPosition(INDEX_ALL),
                        RecyclerViewActions.actionOnItemAtPosition(INDEX_ALL, click()));

        onView(allOf(withId(R.id.rvActivityMainImages), isDisplayed()))
                .perform(RecyclerViewActions.scrollToPosition(0),
                        RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(isRoot()).perform(OrientationChangeAction.orientationLandscape());

        sleep(INTERACTION_DELAY);

        onView(withId(R.id.svActivityDetails)).perform(swipeUp());

        sleep(INTERACTION_DELAY);

        onView(isRoot()).perform(OrientationChangeAction.orientationPortrait());
    }

    @Test
    public void openPhotosOptionAndScrollItem() {
        openDrawer(R.id.drawerLayout);
        sleep(INTERACTION_DELAY);

        onView(allOf(withId(R.id.rvNavigationView), isDisplayed()))
                .perform(RecyclerViewActions.scrollToPosition(INDEX_PHOTO),
                        RecyclerViewActions.actionOnItemAtPosition(INDEX_PHOTO, click()));

        onView(allOf(withId(R.id.rvActivityMainImages), isDisplayed()))
                .perform(RecyclerViewActions.scrollToPosition(0),
                        RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(isRoot()).perform(OrientationChangeAction.orientationLandscape());

        sleep(INTERACTION_DELAY);

        onView(withId(R.id.svActivityDetails)).perform(swipeUp());

        sleep(INTERACTION_DELAY);

        onView(isRoot()).perform(OrientationChangeAction.orientationPortrait());
    }

    @Test
    public void openIllustrationsOptionAndScrollItem() {
        openDrawer(R.id.drawerLayout);
        sleep(INTERACTION_DELAY);

        onView(allOf(withId(R.id.rvNavigationView), isDisplayed()))
                .perform(RecyclerViewActions.scrollToPosition(INDEX_ILLUSTRATION),
                        RecyclerViewActions.actionOnItemAtPosition(INDEX_ILLUSTRATION, click()));

        onView(allOf(withId(R.id.rvActivityMainImages), isDisplayed()))
                .perform(RecyclerViewActions.scrollToPosition(0),
                        RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(isRoot()).perform(OrientationChangeAction.orientationLandscape());

        sleep(INTERACTION_DELAY);

        onView(withId(R.id.svActivityDetails)).perform(swipeUp());

        sleep(INTERACTION_DELAY);

        onView(isRoot()).perform(OrientationChangeAction.orientationPortrait());
    }

    @Test
    public void openVectorsOptionAndScrollItem() {
        openDrawer(R.id.drawerLayout);
        sleep(INTERACTION_DELAY);

        onView(allOf(withId(R.id.rvNavigationView), isDisplayed()))
                .perform(RecyclerViewActions.scrollToPosition(INDEX_VECTOR),
                        RecyclerViewActions.actionOnItemAtPosition(INDEX_VECTOR, click()));

        onView(allOf(withId(R.id.rvActivityMainImages), isDisplayed()))
                .perform(RecyclerViewActions.scrollToPosition(0),
                        RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(isRoot()).perform(OrientationChangeAction.orientationLandscape());

        sleep(INTERACTION_DELAY);

        onView(withId(R.id.svActivityDetails)).perform(swipeUp());

        sleep(INTERACTION_DELAY);

        onView(isRoot()).perform(OrientationChangeAction.orientationPortrait());
    }

    @Test
    public void openAllImages() {
        openDrawer(R.id.drawerLayout);

        onView(allOf(withId(R.id.rvNavigationView), isDisplayed()))
                .perform(RecyclerViewActions.scrollToPosition(INDEX_ALL),
                        RecyclerViewActions.actionOnItemAtPosition(INDEX_ALL, click()));

        for (int i = 0; i < TOTAL_OBJECTS_OPEN + 1; i++) {

            onView(allOf(withId(R.id.rvActivityMainImages), isDisplayed()))
                    .perform(RecyclerViewActions.scrollToPosition(i),
                            RecyclerViewActions.actionOnItemAtPosition(i, click()));

            sleep(INTERACTION_DELAY);
            pressBack();
            sleep(INTERACTION_DELAY);
        }
    }

    @Test
    public void openImagesPhoto() {
        openDrawer(R.id.drawerLayout);

        onView(allOf(withId(R.id.rvNavigationView), isDisplayed()))
                .perform(RecyclerViewActions.scrollToPosition(INDEX_PHOTO),
                        RecyclerViewActions.actionOnItemAtPosition(INDEX_PHOTO, click()));

        for (int i = 0; i < TOTAL_OBJECTS_OPEN + 1; i++) {

            onView(allOf(withId(R.id.rvActivityMainImages), isDisplayed()))
                    .perform(RecyclerViewActions.scrollToPosition(i),
                            RecyclerViewActions.actionOnItemAtPosition(i, click()));

            sleep(INTERACTION_DELAY);
            pressBack();
            sleep(INTERACTION_DELAY);
        }
    }

    @Test
    public void openImagesIllustration() {
        openDrawer(R.id.drawerLayout);

        onView(allOf(withId(R.id.rvNavigationView), isDisplayed()))
                .perform(RecyclerViewActions.scrollToPosition(INDEX_ILLUSTRATION),
                        RecyclerViewActions.actionOnItemAtPosition(INDEX_ILLUSTRATION, click()));

        for (int i = 0; i < TOTAL_OBJECTS_OPEN + 1; i++) {

            onView(allOf(withId(R.id.rvActivityMainImages), isDisplayed()))
                    .perform(RecyclerViewActions.scrollToPosition(i),
                            RecyclerViewActions.actionOnItemAtPosition(i, click()));

            sleep(INTERACTION_DELAY);
            pressBack();
            sleep(INTERACTION_DELAY);
        }
    }

    @Test
    public void openImagesVector() {
        openDrawer(R.id.drawerLayout);

        onView(allOf(withId(R.id.rvNavigationView), isDisplayed()))
                .perform(RecyclerViewActions.scrollToPosition(INDEX_VECTOR),
                        RecyclerViewActions.actionOnItemAtPosition(INDEX_VECTOR, click()));

        for (int i = 0; i < TOTAL_OBJECTS_OPEN + 1; i++) {

            onView(allOf(withId(R.id.rvActivityMainImages), isDisplayed()))
                    .perform(RecyclerViewActions.scrollToPosition(i),
                            RecyclerViewActions.actionOnItemAtPosition(i, click()));

            sleep(INTERACTION_DELAY);
            pressBack();
            sleep(INTERACTION_DELAY);
        }
    }
}
