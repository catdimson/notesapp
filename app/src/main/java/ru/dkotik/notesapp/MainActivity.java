package ru.dkotik.notesapp;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import ru.dkotik.notesapp.model.Note;
import ru.dkotik.notesapp.view.CustomActions;
import ru.dkotik.notesapp.view.detail.NoteDetailFragment;
import ru.dkotik.notesapp.view.list.impl.NotesListFragment;

public class MainActivity extends AppCompatActivity {

    private static final String ARG_NOTE = "ARG_NOTE";
    private Note selectedNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();

        if (savedInstanceState != null && savedInstanceState.containsKey(ARG_NOTE)) {
            selectedNote = savedInstanceState.getParcelable(ARG_NOTE);

            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                fm.beginTransaction()
                        .replace(R.id.details_container, NoteDetailFragment.newInstance(selectedNote))
                        .replace(R.id.fragment_container, new NotesListFragment())
                        .commit();
            } else {
                fm.beginTransaction()
                        .replace(R.id.fragment_container, NoteDetailFragment.newInstance(selectedNote))
                        .commit();
            }
        }


        fm.setFragmentResultListener(NotesListFragment.RESULT_KEY,this, (requestKey, result) -> {
                selectedNote = result.getParcelable(NotesListFragment.ARG_NOTE);

                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                        fm.beginTransaction()
                                .replace(R.id.details_container, NoteDetailFragment.newInstance(selectedNote))
                                .commit();
                } else {
                        fm.beginTransaction()
                                .replace(R.id.fragment_container, NoteDetailFragment.newInstance(selectedNote))
                                .commit();
                }
            }
        );

        fm.setFragmentResultListener(NoteDetailFragment.KEY_GO_TO_MAIN,this, (requestKey, result) -> {
                selectedNote = result.getParcelable(NotesListFragment.ARG_NOTE);

                fm.beginTransaction()
                        .replace(R.id.fragment_container, new NotesListFragment())
                        .commit();
            }
        );
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            Fragment fragment = fm.findFragmentById(R.id.fragment_container);
            if (!(fragment instanceof CustomActions) || !((CustomActions) fragment).onBackPressed()) {
                super.onBackPressed();
            }
        } else {
            super.onBackPressed();
        }
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        if (selectedNote != null) {
            outState.putParcelable(ARG_NOTE, selectedNote);
        }
    }
}