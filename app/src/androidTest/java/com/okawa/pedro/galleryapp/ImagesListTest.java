package com.okawa.pedro.galleryapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;

import com.okawa.pedro.galleryapp.database.ImageRepository;
import com.okawa.pedro.galleryapp.ui.main.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import greendao.ImageData;

import static android.os.SystemClock.sleep;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.contrib.DrawerActions.openDrawer;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by pokawa on 24/11/15.
 */
public class ImagesListTest {

    private static final int INITIAL_DELAY = 2000;
    private static final int TRANSITION_DELAY = 1000;

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setup() {
        sleep(INITIAL_DELAY);
    }

    @Test
    public void openImagesNoFilter() {
        openDrawer(R.id.drawerLayout);

        onView(withText(ImageData.TYPE_ALL)).perform(click());

        for (int i = 0; i < ImageRepository.SELECT_LIMIT + 1; i++) {

            onView(allOf(withId(R.id.rvActivityMainImages), isDisplayed()))
                    .perform(RecyclerViewActions.scrollToPosition(i),
                            RecyclerViewActions.actionOnItemAtPosition(i, click()));

            sleep(TRANSITION_DELAY);
            pressBack();
            sleep(TRANSITION_DELAY);
        }
    }

    @Test
    public void openImagesPhoto() {
        openDrawer(R.id.drawerLayout);

        onView(withText(ImageData.TYPE_PHOTO)).perform(click());

        for (int i = 0; i < ImageRepository.SELECT_LIMIT + 1; i++) {

            onView(allOf(withId(R.id.rvActivityMainImages), isDisplayed()))
                    .perform(RecyclerViewActions.scrollToPosition(i),
                            RecyclerViewActions.actionOnItemAtPosition(i, click()));

            sleep(TRANSITION_DELAY);
            pressBack();
            sleep(TRANSITION_DELAY);
        }
    }

    @Test
    public void openImagesIllustration() {
        openDrawer(R.id.drawerLayout);

        onView(withText(ImageData.TYPE_ILLUSTRATION)).perform(click());

        for (int i = 0; i < ImageRepository.SELECT_LIMIT + 1; i++) {

            onView(allOf(withId(R.id.rvActivityMainImages), isDisplayed()))
                    .perform(RecyclerViewActions.scrollToPosition(i),
                            RecyclerViewActions.actionOnItemAtPosition(i, click()));

            sleep(TRANSITION_DELAY);
            pressBack();
            sleep(TRANSITION_DELAY);
        }
    }

    @Test
    public void openImagesVector() {
        openDrawer(R.id.drawerLayout);

        onView(withText(ImageData.TYPE_VECTOR)).perform(click());

        for (int i = 0; i < ImageRepository.SELECT_LIMIT + 1; i++) {

            onView(allOf(withId(R.id.rvActivityMainImages), isDisplayed()))
                    .perform(RecyclerViewActions.scrollToPosition(i),
                            RecyclerViewActions.actionOnItemAtPosition(i, click()));

            sleep(TRANSITION_DELAY);
            pressBack();
            sleep(TRANSITION_DELAY);
        }
    }
}
