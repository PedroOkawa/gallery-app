package com.okawa.pedro.galleryapp;

import android.support.annotation.StringRes;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.okawa.pedro.galleryapp.actions.OrientationChangeAction;
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
import static com.okawa.pedro.galleryapp.matchers.EditTextMatcher.withText;
import static com.okawa.pedro.galleryapp.matchers.RecyclerViewMatcher.withRecyclerView;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by pokawa on 24/11/15.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class GalleryAppTests {

    private static final int NO_TYPE_DEFINED = -1;
    private static final int INDEX_ALL = 0;
    private static final int INDEX_PHOTO = 1;
    private static final int INDEX_ILLUSTRATION = 2;
    private static final int INDEX_VECTOR = 3;

    private static final int TOTAL_OBJECTS_OPEN = 5;

    private static final int INITIAL_DELAY = 4000;
    private static final int INTERACTION_DELAY = 2000;

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
        openNavigationOption(INDEX_ALL);
        openDetailsSwipeAndOrientation();
    }

    @Test
    public void openPhotosOptionAndScrollItem() {
        openNavigationOption(INDEX_PHOTO);
        openDetailsSwipeAndOrientation();
    }

    @Test
    public void openIllustrationsOptionAndScrollItem() {
        openNavigationOption(INDEX_ILLUSTRATION);
        openDetailsSwipeAndOrientation();
    }

    @Test
    public void openVectorsOptionAndScrollItem() {
        openNavigationOption(INDEX_VECTOR);
        openDetailsSwipeAndOrientation();
    }

    @Test
    public void openAllImages() {
        openNavigationOption(INDEX_ALL);

        for(int i = 0; i < TOTAL_OBJECTS_OPEN; i++) {
            checkDetailsActivityContent(i, NO_TYPE_DEFINED);
        }
    }

    @Test
    public void openImagesPhoto() {
        openNavigationOption(INDEX_PHOTO);

        for(int i = 0; i < TOTAL_OBJECTS_OPEN; i++) {
            checkDetailsActivityContent(i, R.string.type_photo);
        }
    }

    @Test
    public void openImagesIllustration() {
        openNavigationOption(INDEX_ILLUSTRATION);

        for(int i = 0; i < TOTAL_OBJECTS_OPEN; i++) {
            checkDetailsActivityContent(i, R.string.type_illustration);
        }
    }

    @Test
    public void openImagesVector() {
        openNavigationOption(INDEX_VECTOR);

        for(int i = 0; i < TOTAL_OBJECTS_OPEN; i++) {
            checkDetailsActivityContent(i, R.string.type_vector);
        }
    }

    @Test(expected = NoMatchingViewException.class)
    public void checkVectorOnPhotosList() {
        openNavigationOption(INDEX_PHOTO);

        for(int i = 0; i < TOTAL_OBJECTS_OPEN; i++) {
            checkTextOnRecyclerItem(i, R.string.type_vector);
        }
    }

    @Test(expected = NoMatchingViewException.class)
    public void checkIllustrationOnPhotosList() {
        openNavigationOption(INDEX_PHOTO);

        for(int i = 0; i < TOTAL_OBJECTS_OPEN; i++) {
            checkTextOnRecyclerItem(i, R.string.type_illustration);
        }
    }

    @Test(expected = NoMatchingViewException.class)
    public void checkVectorOnIllustrationsList() {
        openNavigationOption(INDEX_ILLUSTRATION);

        for(int i = 0; i < TOTAL_OBJECTS_OPEN; i++) {
            checkTextOnRecyclerItem(i, R.string.type_vector);
        }
    }

    @Test(expected = NoMatchingViewException.class)
    public void checkPhotoOnIllustrationsList() {
        openNavigationOption(INDEX_ILLUSTRATION);

        for(int i = 0; i < TOTAL_OBJECTS_OPEN; i++) {
            checkTextOnRecyclerItem(i, R.string.type_photo);
        }
    }

    @Test(expected = NoMatchingViewException.class)
    public void checkPhotoOnVectorsList() {
        openNavigationOption(INDEX_VECTOR);

        for(int i = 0; i < TOTAL_OBJECTS_OPEN; i++) {
            checkTextOnRecyclerItem(i, R.string.type_photo);
        }
    }

    @Test(expected = NoMatchingViewException.class)
    public void checkIllustrationOnVectorsList() {
        openNavigationOption(INDEX_VECTOR);

        for(int i = 0; i < TOTAL_OBJECTS_OPEN; i++) {
            checkTextOnRecyclerItem(i, R.string.type_illustration);
        }
    }

    private void openDetailsSwipeAndOrientation() {
        onView(allOf(withId(R.id.rvActivityMainImages), isDisplayed()))
                .perform(RecyclerViewActions.scrollToPosition(0),
                        RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(isRoot()).perform(OrientationChangeAction.orientationLandscape());

        sleep(INTERACTION_DELAY);

        onView(withId(R.id.svActivityDetails)).perform(swipeUp());

        sleep(INTERACTION_DELAY);

        onView(isRoot()).perform(OrientationChangeAction.orientationPortrait());
    }

    private void openNavigationOption(int option) {
        openDrawer(R.id.drawerLayout);

        sleep(INTERACTION_DELAY);

        onView(allOf(withId(R.id.rvNavigationView), isDisplayed()))
                .perform(RecyclerViewActions.scrollToPosition(option),
                        RecyclerViewActions.actionOnItemAtPosition(option, click()));

        sleep(INTERACTION_DELAY);
    }

    private void checkTextOnRecyclerItem(int position, @StringRes int stringId) {
        sleep(INTERACTION_DELAY);

        onView(allOf(withId(R.id.rvActivityMainImages), isDisplayed()))
                .perform(RecyclerViewActions.scrollToPosition(position));

        sleep(INTERACTION_DELAY);

        onView(withRecyclerView(R.id.rvActivityMainImages)
                .atPositionOnView(position, R.id.tvImageType))
                .check(ViewAssertions.matches(withText(stringId)));

        sleep(INTERACTION_DELAY);
    }

    private void checkDetailsActivityContent(int position, @StringRes int stringId) {
        sleep(INTERACTION_DELAY);

        onView(allOf(withId(R.id.rvActivityMainImages), isDisplayed()))
                .perform(RecyclerViewActions.scrollToPosition(position),
                        RecyclerViewActions.actionOnItemAtPosition(position, click()));

        sleep(INTERACTION_DELAY);

        if(stringId != -1) {
            onView(withId(R.id.tvImageType))
                    .check(ViewAssertions.matches(withText(stringId)));
        }

        sleep(INTERACTION_DELAY);

        onView(withId(R.id.tvActivityDetailsCategories))
                .check(ViewAssertions.matches(isDisplayed()));

        onView(withId(R.id.tvActivityDetailsContributor))
                .check(ViewAssertions.matches(isDisplayed()));

        sleep(INTERACTION_DELAY);

        pressBack();

        sleep(INTERACTION_DELAY);
    }
}