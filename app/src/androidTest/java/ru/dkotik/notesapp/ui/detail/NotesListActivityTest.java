package ru.dkotik.notesapp.ui.detail;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.view.View;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import ru.dkotik.notesapp.MainActivity;
import ru.dkotik.notesapp.R;

@RunWith(AndroidJUnit4.class)
public class NotesListActivityTest {

    private ActivityScenario<MainActivity> scenario;

    @Before
    public void setup() {
        scenario = ActivityScenario.launch(MainActivity.class);
    }

    @Test
    public void activityList_ScrollToView() {
        onView(withId(R.id.notes_container))
            .perform(
                RecyclerViewActions.scrollTo(
                        hasDescendant(withText("Заголовок 77"))
                )
            );
    }

    @Test
    public void activityList_ScrollToPosition() {
        onView(withId(R.id.notes_container))
            .perform(
                RecyclerViewActions.scrollToPosition(50)
            );
    }

    @Test
    public void activityList_addNoteAndScroll() {
        onView(withId(R.id.fab)).perform(click());
        onView(withId(R.id.title)).perform(click());
        onView(withId(R.id.title)).perform(replaceText("Заголовок 333"), closeSoftKeyboard());
        onView(withId(R.id.description)).perform(replaceText("Описание 333"), closeSoftKeyboard());
        onView(withId(R.id.btn_save)).perform(click());
        onView(isRoot()).perform(delay());
        onView(withId(R.id.notes_container))
                .perform(
                        RecyclerViewActions.scrollTo(
                                hasDescendant(withText("Заголовок 333"))
                        )
                );
    }

    private ViewAction delay() {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isRoot();
            }

            @Override
            public String getDescription() {
                return "Ждём 2 секунды";
            }

            @Override
            public void perform(UiController uiController, View view) {
                uiController.loopMainThreadForAtLeast(2_000);
            }
        };
    }

    @After
    public void close() {
        scenario.close();
    }
}
