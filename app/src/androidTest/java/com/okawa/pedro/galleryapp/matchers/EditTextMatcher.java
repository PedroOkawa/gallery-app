package com.okawa.pedro.galleryapp.matchers;

import android.support.annotation.StringRes;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.core.deps.guava.base.Optional;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.internal.util.Checks;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.TextView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pokawa on 25/11/15.
 */
public class EditTextMatcher {

    public static Matcher<View> withText(final @StringRes int stringId) {
        Checks.checkNotNull(stringId);
        return new BoundedMatcher<View, TextView>(TextView.class) {
            @Override
            protected boolean matchesSafely(TextView item) {
                if(item.getText()
                        .equals(InstrumentationRegistry.getTargetContext().getString(stringId))) {
                    return true;
                } else {
                    List<View> views = new ArrayList<View>();
                    views.add(item);
                    throw new NoMatchingViewException.Builder()
                            .withAdapterViews(views)
                            .withAdapterViewWarning(Optional.<String>absent())
                            .withViewMatcher(this)
                            .withRootView(item)
                            .build();
                }
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("with string ID: ");
            }
        };
    }

}
