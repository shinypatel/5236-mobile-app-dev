package com.osuevents;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class BookmarkTest {

    @Rule
    public ActivityTestRule <EventListActivity> mActivityTestRule = new ActivityTestRule <>( EventListActivity.class );

    @Test
    public void bookmarkTest () {
        ViewInteraction tabView = onView(
                allOf( childAtPosition(
                        childAtPosition(
                                withId( R.id.tabs ),
                                0 ),
                        1 ),
                        isDisplayed() ) );
        tabView.perform( click() );

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep( 6000 );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction recyclerView = onView(
                allOf( withId( R.id.list ),
                        withParent( allOf( withId( R.id.pager ),
                                childAtPosition(
                                        withClassName( is( "android.support.v4.widget.NestedScrollView" ) ),
                                        0 ) ) ),
                        isDisplayed() ) );
        recyclerView.perform( actionOnItemAtPosition( 0, click() ) );

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep( 1000 );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction actionMenuItemView = onView(
                allOf( withId( R.id.action_bookmark ), withContentDescription( "Bookmark" ),
                        childAtPosition(
                                childAtPosition(
                                        withId( R.id.toolbar ),
                                        1 ),
                                0 ),
                        isDisplayed() ) );
        actionMenuItemView.perform( click() );

        pressBack();

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep( 1000 );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction recyclerView2 = onView(
                allOf( withId( R.id.list ),
                        withParent( allOf( withId( R.id.pager ),
                                childAtPosition(
                                        withClassName( is( "android.support.v4.widget.NestedScrollView" ) ),
                                        0 ) ) ),
                        isDisplayed() ) );
        recyclerView2.perform( actionOnItemAtPosition( 0, click() ) );

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep( 1000 );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction actionMenuItemView2 = onView(
                allOf( withId( R.id.action_bookmark ), withContentDescription( "Bookmark" ),
                        childAtPosition(
                                childAtPosition(
                                        withId( R.id.toolbar ),
                                        1 ),
                                0 ),
                        isDisplayed() ) );
        actionMenuItemView2.perform( click() );

        pressBack();

    }

    private static Matcher <View> childAtPosition (
            final Matcher <View> parentMatcher, final int position ) {

        return new TypeSafeMatcher <View>() {
            @Override
            public void describeTo ( Description description ) {
                description.appendText( "Child at position " + position + " in parent " );
                parentMatcher.describeTo( description );
            }

            @Override
            public boolean matchesSafely ( View view ) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches( parent )
                        && view.equals( ((ViewGroup) parent).getChildAt( position ) );
            }
        };
    }
}
