package ru.dkotik.notesapp.ui.detail;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.os.Bundle;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;

import ru.dkotik.notesapp.R;
import ru.dkotik.notesapp.domain.entity.Note;

@RunWith(AndroidJUnit4.class)
public class NoteDetailFragmentTest {

    private FragmentScenario<NoteDetailFragment> scenario;
    private Bundle args;
    private Note note;

    private final SimpleDateFormat timeFormat = new SimpleDateFormat("dd.MM.yyyy - HH:mm", Locale.getDefault());
    private final Calendar calendar = Calendar.getInstance();
    private static final String ARG_NOTE = "ARG_NOTE";

    @Before
    public void setUp() {
        args = new Bundle();
        note = new Note(
                UUID.randomUUID().toString(),"Заголовок 777",
                timeFormat.format(calendar.getTime()), "Рыба текст 777"
        );
        args.putParcelable(ARG_NOTE, note);
        scenario = FragmentScenario.launchInContainer(NoteDetailFragment.class, args);
    }

    @Test
    public void fragment_testSetArgsWhenCreateFragment() {
        ViewAssertion assertion = matches(ViewMatchers.withText("Рыба текст 777"));
        onView(withId(R.id.note_description)).check(assertion);
    }

    @Test
    public void fragment_testDisplayDetails() {
        Note newNote = new Note(
                UUID.randomUUID().toString(),"Заголовок 555",
                timeFormat.format(calendar.getTime()), "Рыба текст 555"
        );

        FragmentScenario.FragmentAction<NoteDetailFragment> fragmentAction = fragment -> {
            fragment.displayDetails(newNote);
        };
        scenario.onFragment(fragmentAction);

        Matcher matcher = ViewMatchers.withText("Заголовок 555");
        ViewAssertion assertion = matches(matcher);
        onView(withId(R.id.note_title)).check(assertion);
    }
}